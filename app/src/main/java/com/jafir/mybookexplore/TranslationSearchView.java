package com.jafir.mybookexplore;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by jafir on 16/9/7. 这个类 事件触发之后header会向上移
 * <p>
 * 搜索动作 展示数据
 * 内部包含一个 header  一个content 还有一个editText 和  搜索结果的listView
 * 点击搜索 关闭header 然后搜索 列出结果在listView
 * 点击取消或者 阴影 打开header 退出搜索
 */
public class TranslationSearchView extends ViewGroup {

    private static final String TAG = TranslationSearchView.class
            .getSimpleName();

    private Context context;

    /**
     * 偏移量（为header的高度）
     */
    private int offY;

    /**
     * 滚动的事件
     */
    private long duration = 700;

    /**
     * 差值器 默认为匀速
     */
    private Interpolator interpolator = new LinearInterpolator();
    /**
     * 出去的属性动画
     */
    private ValueAnimator outAnimator;
    /**
     * 进入的属性动画
     */
    private ValueAnimator inAnimator;
    /**
     * header是否滚出去
     */
    private boolean isOpen = true;
    /**
     * 是否动画进行中
     */
    private boolean isRunning = false;
    /**
     * 搜索按钮
     */
    private EditText search;
    /**
     * 取消按钮
     */
    private TextView cancel;
    // 是否是灰色透明界面
    private boolean isApha = false;
    /**
     * titlebar的高度
     */
    private int titlebarHeight;
    /**
     * listView的包裹父布局
     */
    private RelativeLayout listLayout;
    /**
     * 搜索的layout
     */
    private RelativeLayout searchLayout;
    /**
     * 用于遮盖editText 优先获取点击事件 然后触发点击 弹出软键盘
     */
    private TextView click;

    // #################################listView搜索结果相关#############################################

    /**
     * 搜索条件
     */
    private String mCondition;

    /**
     * 搜索结果集合
     */
    private List mData;

    private BaseAdapter mAdapter;

    private View mListView;

    // ##############################接口定义##################################
    private SearchDataLoadListener searchDataLoadListener;
    private OpenListener openListener;

    private View header;

    private View content;

    private int lastOffY = 0;

    private View editText;

    private View list;

    public TranslationSearchView(Context context) {
        super(context);
        init(context);
    }

    public TranslationSearchView(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public TranslationSearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * 测量     需要调用requestlayout 才会重新调用此方法和onlayout方法
     * 这里的测量完成 动态改变子view大小的工作
     * 如果是 打开状态 =》 关闭状态   需要把高度增加一个header的高度
     * 这样 上移的时候就不会出现 没有内容的情况，上移之后高度刚好
     * 如果是 关闭状态 =》打开状态，需要先向下移动，移动完毕之后，把高度设置为原始高度
     * 这样就不会出现 内容显示不完的情况
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        View head = getChildAt(0);
        int titlbarHeight = 0;
        if (head != null) {
            titlbarHeight = head.getMeasuredHeight();
        }
        View edit = getChildAt(1);
        int editeHeight = 0;
        if (edit != null) {
            editeHeight = edit.getMeasuredHeight();
        }
        View content = getChildAt(2);
        int off = 0;
//		判断是否打开 动态设置高度
        if (!isOpen) {
            off = titlbarHeight;
        } else {
            off = 0;
        }
        if (content != null) {
            content.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    sizeHeight - editeHeight - titlbarHeight + off));
        }
        View list = getChildAt(3);
        if (list != null) {
            list.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                    sizeHeight - editeHeight - titlbarHeight + off));
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw || h != oldh) {
            if (getChildAt(0) != null) {
                titlebarHeight = getChildAt(0).getMeasuredHeight();
            }
        }
        /**
         * onSizeChanged之中调用初始化动画 这个方法是在size大小变化的时候调用的
         */
        initAnimator();
    }

    /**
     * 滚动，向上移动的时候，利用offsetTopAndBottom这方法移动。
     * 利用此方法为了解决 多次requestlayout卡顿的现象
     * <p>
     * <p>
     * 移动之前已经绘制了一次，高度已经超出屏幕一部分，然后移动，
     * 移动完毕之后，高度刚刚好
     * <p>
     * 需要用一个lastOffY来记录上一次的移动位置，然后相减得出差值，移动
     * lastOffY需要在offY 初始化和重新赋值的时候 重新赋值
     * <p>
     * 此方法只用于上移，因为下移的时候，header在屏幕之外，没有绘制，此方法让它下移，
     * 它并没有视图，所以下移是空白的
     * <p>
     * 下移还是调用requestLayout方法
     *
     * @param off
     */
    private void scroll(int off) {
        if (header == null || content == null || editText == null || list == null) {
            return;
        }
        header.offsetTopAndBottom(off);
        editText.offsetTopAndBottom(off);
        content.offsetTopAndBottom(off);
        list.offsetTopAndBottom(off);

        lastOffY = offY;

        Log.d("debug", "off:" + off + "scroll:" + header.getTop() + ":" + header.getBottom());
    }

    /**
     * 绘制  上移的时候，上移之前绘制一次（调整content和list高度，然后测量和定位content超出屏幕的部分），那时候offy为0，
     * 移动完毕后，offy为header高度，然后再绘制一次（重新测量header在屏幕之外的位置）
     * 下移的时候，直接requestlayout下移
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        // titlebar
        header = getChildAt(0);
        if (header != null) {
            header.layout(0, 0 - offY, r, 0 - offY + titlebarHeight);
        }
        Log.d("debug", "onlayout:" + header.getTop() + ":" + header.getBottom());
        // editText
        editText = getChildAt(1);
        if (editText != null) {
            int height1 = editText.getMeasuredHeight();
            editText.layout(0, header.getBottom(), r, header.getBottom() + height1);
        }

        // content
        content = getChildAt(2);
        if (content != null) {
            int contentB = 0;
            if (!isOpen) {
                contentB = editText.getBottom() + content.getMeasuredHeight();
            } else {
                contentB = b;
            }
            content.layout(0, editText.getBottom(), r, contentB);
        }
        // 搜索结果的list
        list = getChildAt(3);
        if (list != null) {
            int listB = 0;
            if (!isOpen) {
                listB = editText.getBottom() + list.getMeasuredHeight();
            } else {
                listB = b;
            }
            list.layout(0, editText.getBottom(), r, listB);
        }
    }

    /**
     * 初始化动画
     */
    private void initAnimator() {
        if (outAnimator == null) {
            outAnimator = ValueAnimator.ofInt(0, titlebarHeight);
            outAnimator.setInterpolator(interpolator);
            outAnimator.setDuration(duration);
            outAnimator
                    .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {

                            offY = (Integer) animation.getAnimatedValue();
                            BigDecimal alpha = new BigDecimal(offY).divide(
                                    new BigDecimal(titlebarHeight * 3), 4,
                                    BigDecimal.ROUND_UP);
                            float a = alpha.floatValue();
                            listLayout.setAlpha(a);
                            scroll(lastOffY - offY);
//							等到完全关闭了之后 再requestLayout一次 让titlebar重新layout在屏幕之外
                            if (offY == titlebarHeight) {
                                requestLayout();
                            }

//							
                        }
                    });

            outAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isRunning = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isRunning = false;
                    cancel.setVisibility(VISIBLE);
                    click.setVisibility(GONE);
                    isApha = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }

        if (inAnimator == null) {
            inAnimator = ValueAnimator.ofInt(titlebarHeight, 0);
            inAnimator.setInterpolator(interpolator);
            inAnimator.setDuration(duration);
            inAnimator
                    .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            offY = (Integer) animation.getAnimatedValue();
                            //比例
                            float fraction = animation.getAnimatedFraction();
                            BigDecimal alpha = new BigDecimal(offY).divide(
                                    new BigDecimal(titlebarHeight * 3), 4,
                                    BigDecimal.ROUND_UP);
                            float a = alpha.floatValue();
                            listLayout.setAlpha(a);
//                            requestLayout();
//							content.setPadding(0,0 , 0,titlebarHeight-offY);
//							list.setPadding(0,0 , 0,titlebarHeight-offY);
                            scroll(lastOffY - offY);
                        }
                    });
            inAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    isRunning = true;
                    hideSoftInputFromWindow(search);
                    cancel.setVisibility(GONE);
                    click.setVisibility(VISIBLE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    isRunning = false;

                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }

    }

    /**
     * 在完成了 xml的inflate之后调用 如果是在xml之后在代码中动态插入view就要用到这个方法 比如这里的searchlayout
     * 它的位置index是1 第二个
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        searchLayout = (RelativeLayout) View.inflate(context,
                R.layout.search_layout, null);
        search = (EditText) searchLayout.findViewById(R.id.search);
        cancel = (TextView) searchLayout.findViewById(R.id.cancel);
        click = (TextView) searchLayout.findViewById(R.id.click_open);

        search.clearFocus();
        search.setCursorVisible(false);
        search.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        listLayout = new RelativeLayout(context);
        listLayout.setBackgroundColor(Color.BLACK);
        listLayout.setAlpha(0);
        listLayout.setVisibility(GONE);
        /**
         * edittext的位置在第二个
         */
        if (getChildCount() >= 1) {
            addView(searchLayout, 1);
            addView(listLayout);
        }
        initListner();
    }

    private void initListner() {
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                open();

            }
        });

        click.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isOpen()) {
                    outKeyBoard();
                    close();

                }
            }
        });

        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                mCondition = s.toString();
                if (TextUtils.isEmpty(s.toString())) {
                    if (mData != null) {
                        mData.clear();
                        listLayout.setBackgroundColor(Color.BLACK);
                        listLayout.setAlpha(0.3f);
                        mAdapter.notifyDataSetChanged();
                        mListView.setVisibility(GONE);
                    }
                } else {
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_SEARCH
                        || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    mCondition = search.getText().toString();
                    // 搜索
                    if (searchDataLoadListener != null) {
                        searchDataLoadListener.onLoad(mCondition);
                    }
                    return true;
                }
                return false;
            }
        });

        /**
         * 灰色阴影点击的时候 恢复
         */
        listLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isApha) {
                    open();
                    isApha = false;
                }
            }
        });
    }

    private void init(Context context) {
        this.context = context;

    }

    /**
     * titlebar进入
     */
    public void open() {
        if (isRunning) {
            return;
        }
        search.setText("");
        search.setCursorVisible(false);
        search.clearFocus();
        listLayout.setBackgroundColor(Color.BLACK);
        if (mData != null) {
            mData.clear();
            mAdapter.notifyDataSetChanged();
        }
        listLayout.setVisibility(GONE);
        if (inAnimator != null) {
            isOpen = true;
//			点击取消的时候
//			先requestlayout 下移之后  获取新的高度 
            inAnimator.start();
            if (openListener != null) {
                openListener.onOpenChanged(true);
            }
        }

    }

    /**
     * titlebar出去
     */
    public void close() {
        if (isRunning) {
            return;
        }
        search.requestFocus();
        search.setCursorVisible(true);
        listLayout.setVisibility(VISIBLE);
        listLayout.setAlpha(0);

        if (outAnimator != null) {
            isOpen = false;
//			content.setPadding(0,0 , 0,0);
//			list.setPadding(0,0 , 0,0);
//			点击搜索的时候
//			先requestlayout 获取新的高度  然后上移之后正合适
            requestLayout();
            outAnimator.start();

            if (openListener != null) {
                openListener.onOpenChanged(false);
            }
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param editText
     */
    public void hideSoftInputFromWindow(EditText editText) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

    }

    /**
     * 延迟弹出软键盘
     */
    public void outKeyBoard() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(search, 0);
            }
        }, duration);
    }

    // ######################################初始化搜索和list搜索结果相关#################################################

    /**
     * 必须调用 不然抛异常
     *
     * @param listView
     * @param adapter
     */
    public void setView(View listView, BaseAdapter adapter) {
        this.mListView = listView;
        this.mAdapter = adapter;
        if (mListView != null) {
            mListView.setVisibility(GONE);
        }
        listLayout.addView(mListView);
    }

    public void showData(List data) {

        if (data.size() == 0 || data == null) {
            Toast.makeText(context, "沒有搜索到联系人", Toast.LENGTH_SHORT).show();
            listLayout.setBackgroundColor(Color.BLACK);
            listLayout.setAlpha(0.3f);
            mListView.setVisibility(GONE);
        } else {
            mListView.setVisibility(VISIBLE);
            listLayout.setBackgroundColor(Color.WHITE);
            listLayout.setAlpha(1);
        }
        this.mData = data;
        mAdapter.notifyDataSetChanged();
    }

    // ###################################################### 接口
    // ####################################################################################

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    // 接口
    public interface SearchDataLoadListener {
        void onPreDataLoad();

        void onLoad(String condition);

        void onDataLoaded();
    }

    public interface OpenListener {
        void onOpenChanged(boolean isOpen);
    }

    public OpenListener getOpenListener() {
        return openListener;
    }

    public void setOpenListener(OpenListener openListener) {
        this.openListener = openListener;
    }

    public SearchDataLoadListener getSearchDataLoadListener() {
        return searchDataLoadListener;
    }

    public void setSearchDataLoadListener(
            SearchDataLoadListener searchDataLoadListener) {
        this.searchDataLoadListener = searchDataLoadListener;
    }

}

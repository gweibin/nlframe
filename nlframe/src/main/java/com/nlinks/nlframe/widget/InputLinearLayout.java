package com.nlinks.nlframe.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.nlinks.nlframe.R;
import com.nlinks.nlframe.utils.ValidUtils;


/**
 * 项目名称：Minielectric
 * 类描述：TOOD
 * 创建时间：2018/6/29 9:31
 *
 * @author gweibin@linewell.com
 */
public class InputLinearLayout extends LinearLayout {

    public final static String LIMITS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 输入框图标
     */
    protected ImageView mInputIV;
    /**
     * 输入框
     */
    protected EditText mInputET;

    /**
     * 清除密码按钮
     */
    protected ImageView mIconClear;

    /**
     * 密码显示或隐藏按钮
     */
    private ImageView mIconPassword;

    /**
     * 是否显示密码
     */
    private boolean isShowPassword;
    /**
     * 是否是密码输入
     */
    private boolean isInputPassword;


    /**
     * 构造函数
     *
     * @param context
     */
    public InputLinearLayout(Context context) {
        super(context);

    }

    /**
     * 构造函数
     *
     * @param context
     * @param attrs
     */
    public InputLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //将布局加载到自定义的控件中
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_input, this);
        initView(context, attrs);
    }

    protected void initView(Context context, AttributeSet attrs) {
        mInputIV = findViewById(R.id.iv_view);
        mInputET = findViewById(R.id.et_view_input);
        mInputET.addTextChangedListener(textWatcher);
        mInputET.setOnFocusChangeListener(focusListener);

        mIconClear = findViewById(R.id.iv_clear);
        mIconClear.setOnClickListener(onClickListener);

        mIconPassword = findViewById(R.id.view_password);
        mIconPassword.setOnClickListener(showPWListener);

        // 自定义的属性值
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.InputLinearLayout);

        // 默认提示语
        String hint = values.getString(R.styleable.InputLinearLayout_hint);
        boolean isShowIcon = values.getBoolean(R.styleable.InputLinearLayout_showIcon, false);
        mInputET.setHint(hint);
        // 显示图标
        if (isShowIcon) {
            mInputIV.setVisibility(VISIBLE);
        }
        //是否是密码输入
        isInputPassword = values.getBoolean(R.styleable.InputLinearLayout_isPassoword, false);
        if (isInputPassword) {
            mInputET.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mInputET.setKeyListener(DigitsKeyListener.getInstance(LIMITS));
        }
        int textSize = values.getInteger(R.styleable.InputLinearLayout_textSize, 0);
        if (textSize == 0) {
            textSize = 15;
        }
        mInputET.setTextSize(textSize);

        mInputET.setMaxWidth(SizeUtils.dp2px(values.getInt(R.styleable.InputLinearLayout_maxWidth, 200)));

        //输入字数
        int maxLength = values.getInt(R.styleable.InputLinearLayout_maxLength, 11);
        mInputET.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        int color = values.getColor(R.styleable.InputLinearLayout_textColor, context.getResources().getColor(R.color.textDark));
        mInputET.setTextColor(color);

        int textColorHint = values.getColor(R.styleable.InputLinearLayout_textColorHint, context.getResources().getColor(R.color.textGray));
        mInputET.setHintTextColor(textColorHint);
    }

    /**
     * 获取密码输入框对象
     *
     * @return
     */
    public EditText getEditText() {

        return mInputET;
    }

    /**
     * 获取输入框的值
     *
     * @return
     */
    public String getEditTextContent() {
        EditText editText = this.getEditText();
        if (editText != null) {
            return editText.getText().toString();
        } else {
            return "";
        }
    }

    /**
     * 设置文字
     *
     * @param text
     */
    public void setText(String text) {
        mInputET.setText(text);
    }

    /**
     * 设置提示文字
     *
     * @param hint
     */
    public void setHint(int hint) {
        mInputET.setHint(hint);
    }


    /**
     * 清除文本事件
     */
    protected OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mInputET.setText("");
            mInputET.requestFocus();
        }
    };

    /**
     * 显示密码样式事件
     */
    protected OnClickListener showPWListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isShowPassword) {
                isShowPassword = false;
                mIconPassword.setImageResource(R.drawable.icon_view_off);
                mInputET.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                isShowPassword = true;
                mIconPassword.setImageResource(R.drawable.icon_view);
                mInputET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        }
    };

    /**
     * 监听输入框的事件
     */
    protected TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {

                if (mInputET.hasFocus()) {
                    mIconClear.setVisibility(View.VISIBLE);
                    if (isInputPassword) {
                        mIconPassword.setVisibility(VISIBLE);
                    }
                } else {
                    mIconClear.setVisibility(View.GONE);
                    mIconPassword.setVisibility(GONE);
                }

            } else {
                mIconClear.setVisibility(View.GONE);
                mIconPassword.setVisibility(GONE);
            }
        }
    };

    public TextWatcher getTextWatcher() {
        return textWatcher;
    }

    /**
     * 监听焦点事件
     */
    protected OnFocusChangeListener focusListener = new OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if (hasFocus) {
                //有焦点有内容则删除可用
                if (mInputET.getText() != null && !mInputET.getText().toString().equals("")) {
                    mIconClear.setVisibility(View.VISIBLE);
                    if (isInputPassword) {
                        mIconPassword.setVisibility(VISIBLE);
                    }
                } else {
                    mIconClear.setVisibility(View.GONE);
                    mIconPassword.setVisibility(GONE);
                }
            } else {
                mIconClear.setVisibility(View.GONE);
                mIconPassword.setVisibility(GONE);
            }
        }
    };

    /**
     * 设置输入类型
     *
     * @param inputType
     */
    public void setInputType(int inputType) {
        mInputET.setInputType(inputType);
    }

    /**
     * 设置输入限制
     *
     * @param limits
     */
    public void setInputLimits(String limits) {
        mInputET.setKeyListener(DigitsKeyListener.getInstance(limits));
    }

    /**
     * 获取手机号，规则正确才会返回
     *
     * @return
     */
    public String getPhoneValidStr() {
        String phone = mInputET.getText().toString();
        if (!TextUtils.isEmpty(phone) && ValidUtils.isPhoneValid(phone)) {
            return phone;
        } else {
            return "";
        }
    }


    /**
     * 获取密码
     *
     * @return
     */
    public String getPasswordWithRule() {
        String password = getEditTextContent();

        if (TextUtils.isEmpty(password) || password.length() < 6) {
            return "";
        }

        return password;
    }

    /**
     * 获取验证码
     *
     * @return
     */
    public String getVerificationWithRule() {
        String verification = getEditTextContent();

        if (TextUtils.isEmpty(verification) || verification.length() != 6) {
            return "";
        }

        return verification;
    }

    /**
     * 是否符合密码规则
     *
     * @return
     */
    public boolean validatePassword() {
        String password = getEditTextContent();
        if (!ValidUtils.verifyPsw(password)) {
            return false;
        }
        return true;
    }

    /**
     * 禁用编辑
     */
    public void disableEditor() {
        mInputET.removeTextChangedListener(textWatcher);
        mInputET.setOnFocusChangeListener(null);
        mInputET.setFocusable(false);
        mIconClear.setVisibility(GONE);
    }

    /**
     * 启用编辑
     */
    public void enableEditor() {
        mInputET.addTextChangedListener(textWatcher);
        mInputET.setOnFocusChangeListener(focusListener);
        mInputET.setFocusable(true);
        mIconClear.setVisibility(VISIBLE);
    }


}

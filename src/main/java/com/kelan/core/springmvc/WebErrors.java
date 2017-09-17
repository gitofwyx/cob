package com.kelan.core.springmvc;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * WEB错误信息
 *
 */
public abstract class WebErrors {

    /**
     * email正则表达式
     */
    public static final Pattern EMAIL_PATTERN = Pattern
            .compile("^\\w+(\\.\\w+)*@\\w+(\\.\\w+)+$");
    /**
     * username正则表达式
     */
    public static final Pattern USERNAME_PATTERN = Pattern
            .compile("^[0-9a-zA-Z\\u4e00-\\u9fa5\\.\\-@_]+$");

    /**
     * 校验中文、英文、数字或下划线的正则表达式
     */
    public static final Pattern CHS_EN_NUM_PATTERN = Pattern
            .compile("^[0-9a-zA-Z_\\u4e00-\\u9fa5]+$");

    /**
     * 通过HttpServletRequest创建WebErrors
     *
     * @param request 从request中获得MessageSource和Locale，如果存在的话。
     */
    public WebErrors(HttpServletRequest request) {
        WebApplicationContext webApplicationContext = RequestContextUtils
                .getWebApplicationContext(request);
        if (webApplicationContext != null) {
            LocaleResolver localeResolver = RequestContextUtils
                    .getLocaleResolver(request);
            Locale tmpLocale;
            if (localeResolver != null) {
                tmpLocale = localeResolver.resolveLocale(request);
                this.messageSource = webApplicationContext;
                this.locale = tmpLocale;
            }
        }
    }

    public WebErrors() {
    }

    /**
     * 构造器
     *
     * @param messageSource
     * @param locale
     */
    public WebErrors(MessageSource messageSource, Locale locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    public String getMessage(String code, Object... args) {
        if (this.messageSource == null) {
            throw new IllegalStateException("MessageSource cannot be null.");
        }
        return this.messageSource.getMessage(code, args, locale);
    }

    /**
     * 添加错误代码
     *
     * @param code 错误代码
     * @param args 错误参数
     * @see MessageSource#getMessage
     */
    public void addErrorCode(String code, Object... args) {
        this.getErrors().add(getMessage(code, args));
    }

    /**
     * 添加错误代码
     *
     * @param code 错误代码
     * @see MessageSource#getMessage
     */
    public void addErrorCode(String code) {
        this.getErrors().add(getMessage(code));
    }

    /**
     * 添加错误字符串
     *
     * @param error
     */
    public void addErrorString(String error) {
        this.getErrors().add(error);
    }

    /**
     * 添加错误，根据MessageSource是否存在，自动判断为code还是string。
     *
     * @param error
     */
    public void addError(String error) {
        // if messageSource exist
        if (messageSource != null) {
            error = messageSource.getMessage(error, null, error, locale);
        }
        this.getErrors().add(error);
    }

    /**
     * 是否存在错误
     *
     * @return
     */
    public boolean hasErrors() {
        return this.errors != null && this.errors.size() > 0;
    }

    /**
     * 错误数量
     *
     * @return
     */
    public int getCount() {
        return this.errors == null ? 0 : this.errors.size();
    }

    /**
     * 错误列表
     *
     * @return
     */
    public List<String> getErrors() {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        return this.errors;
    }

    /**
     * 将错误信息保存至ModelMap，并返回错误页面。
     *
     * @param model
     * @return 错误页面地址
     * @see ModelMap
     */
    public String showErrorPage(ModelMap model) {
        this.toModel(model);
        return this.getErrorPage();
    }

    /**
     * 将错误信息保存至ModelMap
     *
     * @param model
     */
    public void toModel(Map<String, Object> model) {
        if (model == null) {
            throw new IllegalStateException("system error!");
        }
        if (!this.hasErrors()) {
            throw new IllegalStateException("no errors found!");
        }
        model.put(this.getErrorAttrName(), this.getErrors());
    }

    public boolean ifNull(Object o, String field) {
        if (o == null) {
            this.addErrorCode("error.required", field);
            return true;
        } else {
            return false;
        }
    }

    public boolean ifEmpty(Object[] o, String field) {
        if (o == null || o.length <= 0) {
            this.addErrorCode("error.required", field);
            return true;
        } else {
            return false;
        }
    }

    public boolean ifBlank(String s, String field, int maxLength) {
        if (StringUtils.isBlank(s)) {
            this.addErrorCode("error.required", field);
            return true;
        }
        return ifMaxLength(s, field, maxLength);
    }

    public boolean ifMaxLength(String s, String field, int maxLength) {
        if (s != null && s.length() > maxLength) {
            this.addErrorCode("error.maxLength", field, maxLength);
            return true;
        }
        return false;
    }

    public boolean ifOutOfLength(String s, String field, int minLength,
            int maxLength) {
        if (s == null) {
            this.addErrorCode("error.required", field);
            return true;
        }
        int len = s.length();
        if (len < minLength || len > maxLength) {
            this.addErrorCode("error.outOfLength", field, minLength, maxLength);
            return true;
        }
        return false;
    }

    public boolean ifNotEmail(String email, String field, int maxLength) {
        if (this.ifBlank(email, field, maxLength)) {
            return true;
        }
        Matcher m = EMAIL_PATTERN.matcher(email);
        if (!m.matches()) {
            this.addErrorCode("error.email", field);
            return true;
        }
        return false;
    }

    public boolean ifNotUsername(String username, String field, int minLength,
            int maxLength) {
        if (this.ifOutOfLength(username, field, minLength, maxLength)) {
            return true;
        }
        Matcher m = USERNAME_PATTERN.matcher(username);
        if (!m.matches()) {
            this.addErrorCode("error.username", field);
            return true;
        }
        return false;
    }

    /**
     * 长度校验及中文、英文、数字或下划线校验
     * @param strVal 待校验字符串
     * @param field 待校验值的名称
     * @param minLength 最小长度
     * @param maxLength 最大长度
     * @param isRequired 是否可以为空
     * @return 
     */
    public boolean ifChsEnNum(String strVal, String field, int minLength,
            int maxLength, Boolean isRequired) {
        
        if (isRequired) {
            if (this.ifMaxLength(strVal, field, maxLength)) {
                return true;
            }
        } else {
            if (this.ifOutOfLength(strVal, field, minLength, maxLength)) {
                return true;
            }
        }
        Matcher m = CHS_EN_NUM_PATTERN.matcher(strVal);
        if (!m.matches()) {
            this.addErrorCode("error.chsennum", field);
            return true;
        }
        return false;
    }

    public boolean ifNotExist(Object o, Class<?> clazz, Serializable id) {
        if (o == null) {
            this.addErrorCode("error.notExist", clazz.getSimpleName(), id);
            return true;
        } else {
            return false;
        }
    }

    public void noPermission(Class<?> clazz, Serializable id) {
        this.addErrorCode("error.noPermission", clazz.getSimpleName(), id);
    }

    private MessageSource messageSource;
    private Locale locale;
    private List<String> errors;

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 获得本地化信息
     *
     * @return
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * 设置本地化信息
     *
     * @param locale
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * 获得错误页面的地址
     *
     * @return
     */
    protected abstract String getErrorPage();

    /**
     * 获得错误参数名称
     *
     * @return
     */
    protected abstract String getErrorAttrName();
}

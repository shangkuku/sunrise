package rbk.sunrise.validation;

import javax.validation.groups.Default;

/**
 * 校验分组
 */
public interface Group {

    /**
     * 已经存在的
     * 为了校验分组
     */
    static interface Existing extends Default {

    }

    /**
     * 新增的
     * 为了校验分组
     */
    static interface New extends Default {

    }

}

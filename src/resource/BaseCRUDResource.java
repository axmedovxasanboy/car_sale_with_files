package resource;

import bean.ApiResponse;
import bean.BaseBean;

public interface BaseCRUDResource<T extends BaseBean> {
    ApiResponse add(T bean);

    ApiResponse get(Integer id);

}

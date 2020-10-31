/**
 * @作者 WeLong
 * @博客 $ https://towelong.cn
 * @开源项目 $ https://github.com/ToWeLong
 * @创建时间 2020/9/22 18:05
 */
package com.towelong.zhihu.common.interfaces;

import com.towelong.zhihu.common.annotation.RouteMeta;

public interface MetaPreHandler {
    void handle(RouteMeta meta);
}

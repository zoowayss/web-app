package io.web.app.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.function.Function;

/**
 * @Author: <a href="https://github.com/zooways">zooways</a>
 * @Date: 2023/7/28 14:03
 */

public class PageUtils {

    public static Page of(Pageable pageable) {
        return Page.of(pageable.getPageNumber() + 1, pageable.getPageSize());
    }

    public static org.springframework.data.domain.Page from(IPage page) {
        return new PageImpl(page.getRecords(), of(page), page.getTotal());
    }

    public static Pageable of(IPage page) {
        return PageRequest.of((int)page.getCurrent()-1, (int)page.getSize());
    }

    public static org.springframework.data.domain.Page request(Function<Page, IPage> function, Pageable pageable) {
        return from(function.apply(of(pageable)));
    }

}
package com.phunghung29.microservice.user.utils;

import com.phunghung29.microservice.user.exceptions.BadRequestException;
import com.phunghung29.microservice.user.exceptions.ExceptionCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

public class Utils {

    private Utils() {}

    public static Properties loadProperties(String fileName) {
        try (InputStream input = Utils.class.getClassLoader().getResourceAsStream(fileName)) {

            Properties prop = new Properties();

            if (input == null) {
                throw new IOException();
            }
            prop.load(input);
            return prop;

        } catch (IOException ex) {
            ex.printStackTrace();
            return new Properties();
        }
    }

    public static  <T> Pageable toPageRequestJPA(PageOption option, Class<T> tClass) {
        String sortCondition = option.getSort();
        if (option.getSort().compareTo(PageOption.SortCondition.NO_SORT) == 0) {
            return PageRequest.of(option.getPage(), option.getLimit());
        }
        String packageNameGenericClass = tClass.getPackage().getName();
        boolean isModel = packageNameGenericClass.contains("entities");
        if (!isModel) {
            throw new IllegalArgumentException("Not a entity class");
        }
        boolean flag = false;
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().toLowerCase().contains(sortCondition)) {
                sortCondition = field.getName();
                flag = true;
                break;
            }
        }
        if (!flag) {
            throw new BadRequestException(ExceptionCode.INVALID_FORMAT, "Can not find the sort target");
        }
        Sort sort = Sort.by(sortCondition);
        sort = option.getAsc() ? sort.ascending() : sort.descending();
        return PageRequest.of(option.getPage(), option.getLimit(), sort);
    }
}

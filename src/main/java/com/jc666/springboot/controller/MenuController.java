package com.jc666.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jc666.springboot.common.Constants;
import com.jc666.springboot.entity.Dict;
import com.jc666.springboot.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jc666.springboot.common.Result;
import javax.annotation.Resource;
import java.util.List;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import com.jc666.springboot.service.IMenuService;
import com.jc666.springboot.entity.Menu;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author L0veRamen
 * @since 2022-09-02
 */
@RestController
@RequestMapping("/menu")
    public class MenuController {
            @Resource
            @Qualifier("menuServiceImpl")
            private IMenuService menuService;

            @Resource
            private DictMapper dictMapper;

            @PostMapping
            public Result save(@RequestBody Menu menu) {
                menuService.saveOrUpdate(menu);
                return Result.success();
            }

            @DeleteMapping("/{id}")
            public Result delete(@PathVariable Integer id){
                menuService.removeById(id);
                return Result.success();
            }

            @PostMapping("/del/batch")
            public Result deleteBatch(@RequestBody List<Integer> ids) {
                menuService.removeByIds(ids);
                return Result.success();
            }

            @GetMapping("/ids")
            public Result findAllIds() {
                return Result.success(menuService.list().stream().map(Menu::getId));
            }

            @GetMapping
            public Result findAll(@RequestParam(defaultValue = "") String name){
                return Result.success(menuService.findMenus(name));
            }

            @GetMapping("/{id}")
            public Result findOne(@PathVariable Integer id) {
                return Result.success(menuService.getById(id));
            }

            @GetMapping("/page")
            public Result findPage( @RequestParam String name,
                                    @RequestParam Integer pageNum,
                                    @RequestParam Integer pageSize){
                QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
                queryWrapper.like("name", name);
                queryWrapper.orderByDesc("id");
                return Result.success(menuService.page(new Page<>(pageNum, pageSize), queryWrapper));
            }

            @GetMapping("/icons")
            public Result getIcons() {
                QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("type", Constants.DICT_TYPE_ICON);
                return Result.success(dictMapper.selectList(queryWrapper));
            }


    }

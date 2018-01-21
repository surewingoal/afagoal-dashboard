package com.afagoal.web.login;

import com.afagoal.util.BuildTree;
import com.afagoal.util.Tree;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BaoCai on 18/1/21.
 * Description:
 */
@Controller
public class LoginController {

    @RequestMapping({ "/index" })
    String index(Model model) {

        List<MenuDO> menuDOs = new ArrayList();

        MenuDO menuDO = new MenuDO();
        menuDO.setMenuId((long) 1);
        menuDO.setParentId((long) 0);
        menuDO.setName("1");
        MenuDO menuDO1 = new MenuDO();
        menuDO1.setMenuId((long) 2);
        menuDO1.setParentId((long) 0);
        menuDO1.setName("2");

        menuDOs.add(menuDO);
        menuDOs.add(menuDO1);

        for(int i = 1 ; i < 3 ; i++){
            MenuDO menu = new MenuDO();
            menu.setMenuId((long) i + 1);
            menu.setParentId((long) i);
            menu.setName("菜单"  + i );
            menu.setUrl("main");
            menuDOs.add(menu);
        }

        List<Tree<MenuDO>> trees = new ArrayList<Tree<MenuDO>>();
        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(sysMenuDO.getMenuId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getUrl());
            attributes.put("icon", sysMenuDO.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }

        List<Tree<MenuDO>> menus =  BuildTree.buildList(trees, "0");
        model.addAttribute("menus", menus);
        model.addAttribute("name", "afagoal");

        model.addAttribute("picUrl","/img/photo_s.jpg");
        model.addAttribute("username", "afagoal");
        return "index";
    }

    @RequestMapping({ "/main" })
    public String main(){
        return "main";
    }

}

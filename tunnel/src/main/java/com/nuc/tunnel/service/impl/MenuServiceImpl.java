package com.nuc.tunnel.service.impl;


import com.nuc.tunnel.entity.Menu;
import com.nuc.tunnel.mapper.MenuMapper;
import com.nuc.tunnel.service.MenuService;
import com.nuc.tunnel.until.Icon;
import com.nuc.tunnel.until.MenuVo;
import com.nuc.tunnel.until.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
@Slf4j
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuVo> findMenuTree(Long roleId) {
//        List<Menu> allMenusList = menuMapper.selectAll();
        List<Menu> roleMenusList = menuMapper.selectMenuByRoleId(roleId);
        List<MenuVo> menuVo = getRoleMenuTree(roleMenusList);
        return menuVo;
    }

    /**
     * 根据菜单id删除菜单
     * @param id
     * @return
     */
    @Override
    public void deleteByPrimaryKey(Long id) {
        menuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 新增菜单
     * @param menus
     * @return
     */
    @Override
    public void insert(Menu menus) {
        menuMapper.insert(menus);
    }

    /**
     * 根据菜单id查询菜单信息
     * @param id
     * @return
     */
    @Override
    public Menu selectByPrimaryKey(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<MenuVo> selectAll() {
        List<Menu> menuList = menuMapper.selectAll();
        List<MenuVo> menuVo = getRoleMenuTree(menuList);
        return menuVo;
    }

    /**
     * 根据菜单id修改菜单信息
     * @param menus
     * @return
     */
    @Override
    public void updateByPrimaryKey(Menu menus) {
        menuMapper.updateByPrimaryKey(menus);
    }

    /**
     * 更新角色菜单
     * @param roleId 角色id
     * @param menusIds 菜单id集合
     * @return
     */
    @Override
    public ResultVo updateRoleMenu(Long roleId, String menusIds) {
        String []menuIds;
        //查询该角色拥有的菜单权限列表
        List<Menu> menusList = menuMapper.selectMenuByRoleId(roleId);
        if (!("").equals(menusIds) & menusIds !=null){
            menuIds = menusIds.split(",");
            //如果菜单权限列表为空则新增
            if (menusList.size()==0){
                for (String s : menuIds){
                    Map<String,Long> map = new HashMap<>();
                    Long menusId = Long.parseLong(s);
                    map.put("roleId",roleId);
                    map.put("menusId",menusId);
                    menuMapper.updateRoleMenuByRoleIdAndMenuID(map);
                }
            }else {//如果菜单权限列表不为空
                for (String s:menuIds){
                    for (Menu menus : menusList){
                        //判断新增的菜单是否存在于角色已有的权限中,如果不存在则添加
                        if (!(menus.getId()==Long.parseLong(s))){
                            Map<String,Long> map = new HashMap<>();
                            Long menusId = Long.parseLong(s);
                            map.put("roleId",roleId);
                            map.put("menusId",menusId);
                            menuMapper.updateRoleMenuByRoleIdAndMenuID(map);
                        }
                    }
                }
            }
        }else {
            //如果传入的菜单id为空或者null则删除菜单
            for (Menu menus : menusList){
                menuMapper.deleteRoleMenuByRoleIdAndMenuID(roleId, menus.getId());
            }
        }
        return null;
    }

    @Override
    public List<MenuVo> selectMenuByUserId(Long userId) {
        //用户拥有的所有菜单权限
        List<Menu> userMenusList = menuMapper.selectMenuByUserId(userId);
        //添加父节点到集合中
        List<MenuVo> menuVos =  getRoleMenuTree(userMenusList);
//        log.info("菜单：{}",menuVos);
        return menuVos;
    }


    /**
     * 将menu封装到menuVo对象中
     * @param list
     * @param num
     * @return
     */
    public MenuVo getMenuVo(List<Menu> list, int num){
        MenuVo menuVo = new MenuVo();
        menuVo.setId(list.get(num).getId());
        menuVo.setTitle(list.get(num).getText());
        menuVo.setKey(list.get(num).getId());
        menuVo.setText(list.get(num).getText());
        menuVo.setLink(list.get(num).getLink());
        Icon icon = new Icon();
        icon.setType(list.get(num).getIconType());
        icon.setValue(list.get(num).getIcon());
        menuVo.setIcon(icon);
        menuVo.setGroup(list.get(num).getGroup());
        menuVo.setHideInBreadcrumb(list.get(num).getHideInBreadcrumb());
        menuVo.setParentId(list.get(num).getParentId());
        menuVo.setOrderId(list.get(num).getOrderId()!=null ? list.get(num).getOrderId():99);
        return menuVo;
    }


    /**
     * 加载菜单树
     * @param userMenusList
     * @return
     */
    public List<MenuVo> getRoleMenuTree(List<Menu> userMenusList){
        //父菜单
        List<MenuVo> parentMenuList = new ArrayList<>();
        //子菜单
        List<MenuVo> childMenuList = new ArrayList<>();
        //找出父菜单
        for (int i = 0;i <userMenusList.size();i++){
            if (userMenusList.get(i).getParentId()==null){
                MenuVo parentMenu = getMenuVo(userMenusList,i);
                parentMenuList.add(parentMenu);
            }
        }
        //找出子菜单
        for (int i = 0;i <userMenusList.size();i++){
            for (int j = 0;j<parentMenuList.size();j++){
                if (userMenusList.get(i).getParentId() != null){
                    Long parentId = parentMenuList.get(j).getId();
                    if (parentId.equals(userMenusList.get(i).getParentId())){
                        MenuVo childrenMenu = getMenuVo(userMenusList,i);
                        childrenMenu.setParentId(parentId);
                        childMenuList.add(childrenMenu);
                    }
                }
            }
        }
        //将子菜单跟父菜单匹配
        for (int i = 0;i <parentMenuList.size();i++){
            List<MenuVo> children = new ArrayList<>();
            for (int j = 0;j<childMenuList.size();j++){
                Long parentId = parentMenuList.get(i).getId();
                Long childId = childMenuList.get(j).getParentId();
                if (parentId.equals(childId)){
                    children.add(childMenuList.get(j));
                }
            }
            children.sort((c1,c2)->Integer.valueOf(c1.getOrderId().compareTo(c2.getOrderId())));
            parentMenuList.get(i).setChildren(children);
        }
        return parentMenuList;
    }

    @Override
    public void insertRoleMenu(Long roleId, String menusId) {
        if (!("").equals(menusId) & menusId !=null){
            String menu[] = menusId.split(",");
            for (String s : menu){
//                menuMapper.insertRoleMenu(roleId,Long.parseLong(s));
            }
        }
    }
}

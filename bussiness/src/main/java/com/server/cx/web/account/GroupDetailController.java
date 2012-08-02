package com.server.cx.web.account;

import com.server.cx.entity.account.Group;
import com.server.cx.entity.account.Permission;
import com.server.cx.service.account.AccountManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/account/group/")
public class GroupDetailController {

    @Autowired
    private AccountManager accountManager;

    @RequiresPermissions("group:edit")
    @RequestMapping(value = "update/{id}")
    public String updateForm(Model model) {
        model.addAttribute("allPermissions", Permission.values());
        return "account/groupForm";
    }

    @RequiresPermissions("group:edit")
    @RequestMapping(value = "save/{id}")
    public String save(@ModelAttribute("group") Group group, RedirectAttributes redirectAttributes) {
        accountManager.saveGroup(group);
        redirectAttributes.addFlashAttribute("message", "修改权限组" + group.getName() + "成功");
        return "redirect:/account/group/";
    }

    @ModelAttribute("group")
    public Group getGroup(@PathVariable("id") Long id) {
        return accountManager.getGroup(id);
    }
}

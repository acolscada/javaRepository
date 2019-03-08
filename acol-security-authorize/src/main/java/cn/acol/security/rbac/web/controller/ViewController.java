package cn.acol.security.rbac.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.acol.security.rbac.domain.Resource;
import cn.acol.security.rbac.repository.ResourceRepository;

@Controller
public class ViewController {
	@Autowired
	private ResourceRepository resourceRepository;
	@GetMapping
	@RequestMapping("/resource/editResourceForm/{id}")
	public String editResourceForm(@PathVariable Long id, ModelMap map) {
		//resourceService
		Resource resource = resourceRepository.findById(id).get();
		map.addAttribute("resource", resource);
		return "resource/urlsEdit";
	}
}

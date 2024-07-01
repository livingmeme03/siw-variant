package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import it.uniroma3.siw.model.Editore;

import it.uniroma3.siw.service.EditoreService;

@RestController
public class EditoreRestController {
	
	@Autowired
	private EditoreService editoreService;
	
	@GetMapping("/rest/editore/{id}")
	public Editore getEditore(@PathVariable("id") Long id) {
		return this.editoreService.findById(id);
	}
	
	@GetMapping("/rest/elencoEditori")
	public List<Editore> getElencoManga() {
		return (List<Editore>)this.editoreService.findAll();
	}
}

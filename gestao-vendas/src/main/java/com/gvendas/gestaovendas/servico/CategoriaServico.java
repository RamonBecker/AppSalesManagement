package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.gvendas.gestaovendas.entidades.Categoria;
import com.gvendas.gestaovendas.repository.CategoriaRepositorio;

@Service
public class CategoriaServico {

	@Autowired
	private CategoriaRepositorio categoriaRepositorio;

	public List<Categoria> listarTodas() {

		return categoriaRepositorio.findAll();
	}

	public Optional<Categoria> buscarPorCodigo(Long codigo) {
		return categoriaRepositorio.findById(codigo);
	}

	public Categoria salvar(Categoria categoria) {
		return categoriaRepositorio.save(categoria);
	}

	public Categoria atualizar(Long codigo, Categoria categoria) {
		Categoria categoriaSalvar = validarCategoriaExistente(codigo);
		
		//Pegando a categoria atual e substituindo pela nova, exceto o código
		BeanUtils.copyProperties(categoria, categoriaSalvar, "codigo");
		
		return categoriaRepositorio.save(categoriaSalvar);
	}

	private Categoria validarCategoriaExistente(Long codigo) {
		Optional<Categoria> categoria = buscarPorCodigo(codigo);

		if (categoria.isEmpty()) {
			throw new EmptyResultDataAccessException(1);
		}
		
		return categoria.get();
	}
	
	public void deletar(Long codigo) {
		categoriaRepositorio.deleteById(codigo);
	}
}

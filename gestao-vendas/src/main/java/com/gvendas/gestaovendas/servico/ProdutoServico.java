package com.gvendas.gestaovendas.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gvendas.gestaovendas.entidades.Produto;
import com.gvendas.gestaovendas.excecao.RegraNegocioException;
import com.gvendas.gestaovendas.repository.ProdutoRepositorio;

@Service
public class ProdutoServico {

	@Autowired
	private ProdutoRepositorio produtoRepositorio;

	@Autowired
	private CategoriaServico categoriaServico;

	public List<Produto> listarTodos(Long codigoCategoria) {
		return produtoRepositorio.findByCategoriaCodigo(codigoCategoria);
	}

	public Optional<Produto> buscarPorCodigo(Long codigo, Long codigoCategoria) {
		return produtoRepositorio.findByCodigoAndCategoriaCodigo(codigo, codigoCategoria);
	}

	public Produto salvar(Produto produto) {
		validarCategoriaExistente(produto.getCategoria().getCodigo());
		validarProdutoExistente(produto);
		return produtoRepositorio.save(produto);
	}

	private void validarProdutoExistente(Produto produto) {
		if (produtoRepositorio
				.findByCategoriaCodigoAndDescricao(produto.getCategoria().getCodigo(), produto.getDescricao())
				.isPresent()) {
			throw new RegraNegocioException(
					String.format("O produto %s informado já está cadastrado", produto.getDescricao()));

		}
	}

	private void validarCategoriaExistente(Long codigo) {
		if (codigo == null) {
			throw new RegraNegocioException("A categoria não pode ser vazia");
		}

		if (categoriaServico.buscarPorCodigo(codigo).isEmpty()) {
			throw new RegraNegocioException(String.format("A categoria de código %s informado não existe", codigo));
		}
	}

}

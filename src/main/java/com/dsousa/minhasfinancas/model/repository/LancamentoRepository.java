package com.dsousa.minhasfinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dsousa.minhasfinancas.model.entity.Usuario;

public interface LancamentoRepository extends JpaRepository<Usuario, Long>{

}

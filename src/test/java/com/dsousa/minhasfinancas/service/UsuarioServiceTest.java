package com.dsousa.minhasfinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.dsousa.minhasfinancas.exceptions.ErroAutenticacao;
import com.dsousa.minhasfinancas.exceptions.RegraNegocioException;
import com.dsousa.minhasfinancas.model.entity.Usuario;
import com.dsousa.minhasfinancas.model.repository.UsuarioRepository;
import com.dsousa.minhasfinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {
	
	UsuarioService service;
	
	@MockBean
	UsuarioRepository repository;
	
	@Before
	public void setUp() {
		service = new UsuarioServiceImpl(repository);
	}
	
	@Test(expected = Test.None.class)
	public void deveAutenticarUmUsuarioComSucesso() {
		// cenário
		String email = "email@email.com";
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		// ação
		Usuario result = service.autenticar(email, senha);
		
		// verificação
		Assertions.assertThat(result).isNotNull();
	}
	
	@Test(expected = ErroAutenticacao.class)
	public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		// cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		// ação
		service.autenticar("email@email.com", "senha");
	}
	
	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		// cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		// ação
		service.validarEmail("email@email.com");
	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroQuandoExistirEmailCadastrado() {
		// cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		// ação
		service.validarEmail("email@email.com");
	}
}

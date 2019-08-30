package net.douglashiura.picon.linguagem;

import java.net.URLClassLoader;
import java.util.Deque;

import net.douglashiura.picon.ProblemaDeCompilacaoException;
import net.douglashiura.picon.linguagem.atribuicao.ProcessadorDeCampos;
import net.douglashiura.picon.preguicoso.Objeto;
import sun.misc.Launcher;

public class ProcessadorDeConjunto {

	private Qualificadores qualificadores;

	public ProcessadorDeConjunto(Qualificadores qualificadores) {
		this.qualificadores = qualificadores;
	}

	public void processar(Deque<Parte> emClasse) throws ProblemaDeCompilacaoException {
		Parte parteClasse = emClasse.pop();
		try {
			Class<?> klasse = buscarClasse(parteClasse.valor());
			ProcessadorDeCampos processador = new ProcessadorDeCampos(qualificadores);
			emClasse.pop();
			while (!"}".equals(emClasse.peek().valor())) {
				Parte qualificador = emClasse.pop();
				Objeto<?> objeto = new Objeto<>(klasse,parteClasse);
				qualificadores.put(qualificador.valor(), objeto);
				processador.processar(emClasse, objeto);
			}
			emClasse.pop();
		} catch (ClassNotFoundException e) {
			throw new ProblemaDeCompilacaoException(e, parteClasse);
		}
	}
	
	
	private Class<?> buscarClasse(String parteClasse) throws ClassNotFoundException {
		ClassLoader[] carregadores = {  ClassLoader.getSystemClassLoader(), Thread.currentThread().getContextClassLoader().getParent(), 
				URLClassLoader.getSystemClassLoader(), Launcher.getLauncher().getClassLoader(), Launcher.getLauncher().getClassLoader().getParent() };
		for (ClassLoader carregador : carregadores) {
			System.out.println(carregador.getClass()+" class:"+ parteClasse);
			try {
				return carregar(parteClasse, carregador);
			} catch (ClassNotFoundException semClasse) {
			}
		}
		throw new ClassNotFoundException();
	}

	private Class<?> carregar(String nome, ClassLoader load) throws ClassNotFoundException {
		return load.loadClass(nome);
	}
	
	
}

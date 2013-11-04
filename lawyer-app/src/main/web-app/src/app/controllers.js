/**
 * @ngdoc object
 * @name lawyer.controllers
 * @description
 * Esse eh o modulo para carregar todos os controllers.
 *
 * Assim sao centralizadas a declaracoes de controllers aqui, ao inves de adicionar no {@link lawyer.app} module.
 *
 * @example
 * `angular.module('lawyer.controllers', [` <br>
 *    `'lawyer.home',`<br>
 *    `'lawyer.atividades.popup'`<br>
 *    `'lawyer.outro.controller...'`
 *    `]);`
 *
 */
angular.module('lawyer.controllers', [
    'lawyer.painel',
    'lawyer.atividades.popup',
    'lawyer.empresas',
    'lawyer.empresas.cadastro',
    'lawyer.empresas.edicao',
    'lawyer.empresas.listar',
    'lawyer.pessoas',
    'lawyer.pessoas.listar',
    'lawyer.pessoas.cadastro',
    'lawyer.pessoas.edicao',
    'lawyer.areasAtuacao',
    'lawyer.areasAtuacao.listar',
    'lawyer.areasAtuacao.cadastro',
    'lawyer.areasAtuacao.edicao',
    'lawyer.advogados',
    'lawyer.advogados.listar',
    'lawyer.advogados.cadastro',
    'lawyer.advogados.edicao',
    'lawyer.configuracoes.advocacia',
    'lawyer.configuracoes.conta'
]);

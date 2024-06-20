package br.dc.compiladores.alguma.semantico;

import br.dc.compiladores.alguma.semantico.TabelaDeSimbolos.TipoAlguma;

public class AlgumaSemantico extends AlgumaBaseVisitor<Void> {

    Escopos pilhaDeTabelas = new Escopos();
    String erroSemantico;

    @Override
    public Void visitPrograma(AlgumaParser.ProgramaContext ctx) {
        return super.visitPrograma(ctx);
    }

    @Override
    public Void visitCorpo(AlgumaParser.CorpoContext ctx) { 
        pilhaDeTabelas.criarNovoEscopo();
        
        super.visitCorpo(ctx); 
        pilhaDeTabelas.abandonarEscopo();
        return null;
    }
    

    @Override
    public Void visitDeclaracao_local(AlgumaParser.Declaracao_localContext ctx){
        if (ctx.v1 != null){
            String srtTipoVar = ctx.v1.tipo().getText();
            TabelaDeSimbolos escopoAtual = pilhaDeTabelas.obterEscopoAtual();
            if (!AlgumaSemanticoUtils.ehTipoBasico(srtTipoVar)){
                erroSemantico = "tipo "+srtTipoVar+" nao declarado";
                AlgumaSemanticoUtils.adicionarErroSemantico(ctx.v1.start, erroSemantico);
            }
            TipoAlguma tipoVar = TipoAlguma.INVALIDO;
            switch(srtTipoVar){
                case "literal":
                    tipoVar = TipoAlguma.LITERAL;
                    break;
                case "inteiro":
                    tipoVar = TipoAlguma.INTEIRO;
                    break;
                case "real":
                    tipoVar = TipoAlguma.REAL;
                    break;
                case "logico":
                    tipoVar = TipoAlguma.LOGICO;
                    break;
                default:
                    break;
            }
            for (var v : ctx.v1.identificador()){
                if (escopoAtual.existe(v.getText())){
                    erroSemantico = "identificador "+v.getText()+" ja declarado anteriormente";
                    AlgumaSemanticoUtils.adicionarErroSemantico(v.start, erroSemantico);
                } else{
                    escopoAtual.adicionar(v.getText(), tipoVar);
                }
            }
        } else if (ctx.v2 != null){
        } else if (ctx.v3 != null){
        } else{
        }

        return super.visitDeclaracao_local(ctx);
    }

    @Override
    public Void visitCmdLeia(AlgumaParser.CmdLeiaContext ctx){
        for (var v : ctx.identificador()){
            boolean foiDeclarado = false;
            for (TabelaDeSimbolos t : pilhaDeTabelas.percorrerEscoposAninhados()){
                if (t.existe(v.getText())){
                    foiDeclarado = true;            
                }
            }
            if (!foiDeclarado){
                erroSemantico = "identificador "+v.getText()+" nao declarado";
                AlgumaSemanticoUtils.adicionarErroSemantico(ctx.start, erroSemantico);
            }
        }

        return null;
    }
 
    @Override
    public Void visitCmdEscreva(AlgumaParser.CmdEscrevaContext ctx){
        for (var e : ctx.expressao()){
            visitExpressao(e);
        }

        return null;
    }    

    @Override
    public Void visitCmdAtribuicao(AlgumaParser.CmdAtribuicaoContext ctx)
    { 
        TipoAlguma tipoExpressao = AlgumaSemanticoUtils.verificarTipo(pilhaDeTabelas, ctx.expressao());
        TipoAlguma tipoId = pilhaDeTabelas.obterEscopoAtual().verificar(ctx.identificador().getText());

        if (tipoExpressao.equals(tipoId) || AlgumaSemanticoUtils.ehTipoInteiroEmReal(tipoId, tipoExpressao)){
            return null;
        } else{
            erroSemantico = "atribuicao nao compativel para "+ctx.identificador().getText();
            AlgumaSemanticoUtils.adicionarErroSemantico(ctx.start, erroSemantico);
        }

        return null; 
    }

    @Override
    public Void visitParcela_unario(AlgumaParser.Parcela_unarioContext ctx){
        if (ctx.p1 != null){
            String nomeId = ctx.p1.getText();
            for (var t : pilhaDeTabelas.percorrerEscoposAninhados()){
                if (t.existe(nomeId)){
                    return null;
                }
            }
            erroSemantico = "identificador "+nomeId+" nao declarado";
            AlgumaSemanticoUtils.adicionarErroSemantico(ctx.p1.start, erroSemantico);
        }
        return null;
    }

     
    @Override
    public Void visitExp_aritmetica(AlgumaParser.Exp_aritmeticaContext ctx){
        AlgumaSemanticoUtils.verificarTipo(pilhaDeTabelas, ctx);

        return super.visitExp_aritmetica(ctx);
    }
}
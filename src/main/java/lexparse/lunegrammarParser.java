// $ANTLR 3.4 /home/raph/Projects/lune/antlr-grammar/lunegrammar.g 2012-10-17 17:16:29

package main.java.lexparse;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

import org.antlr.runtime.tree.*;


@SuppressWarnings({"all", "warnings", "unchecked"})
public class lunegrammarParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "COMMENT", "EQ", "ESC_SEQ", "EXPONENT", "FLOAT", "HEX_DIGIT", "ID", "INT", "OCTAL_ESC", "STRING", "UNICODE_ESC", "WS", "'('", "')'", "'->'", "'def'", "'else'", "'fun'", "'if'", "'in'", "'let'", "'then'"
    };

    public static final int EOF=-1;
    public static final int T__16=16;
    public static final int T__17=17;
    public static final int T__18=18;
    public static final int T__19=19;
    public static final int T__20=20;
    public static final int T__21=21;
    public static final int T__22=22;
    public static final int T__23=23;
    public static final int T__24=24;
    public static final int T__25=25;
    public static final int COMMENT=4;
    public static final int EQ=5;
    public static final int ESC_SEQ=6;
    public static final int EXPONENT=7;
    public static final int FLOAT=8;
    public static final int HEX_DIGIT=9;
    public static final int ID=10;
    public static final int INT=11;
    public static final int OCTAL_ESC=12;
    public static final int STRING=13;
    public static final int UNICODE_ESC=14;
    public static final int WS=15;

    // delegates
    public Parser[] getDelegates() {
        return new Parser[] {};
    }

    // delegators


    public lunegrammarParser(TokenStream input) {
        this(input, new RecognizerSharedState());
    }
    public lunegrammarParser(TokenStream input, RecognizerSharedState state) {
        super(input, state);
    }

protected TreeAdaptor adaptor = new CommonTreeAdaptor();

public void setTreeAdaptor(TreeAdaptor adaptor) {
    this.adaptor = adaptor;
}
public TreeAdaptor getTreeAdaptor() {
    return adaptor;
}
    public String[] getTokenNames() { return lunegrammarParser.tokenNames; }
    public String getGrammarFileName() { return "/home/raph/Projects/lune/antlr-grammar/lunegrammar.g"; }


    public static class top_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "top"
    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:12:1: top : ( def )+ EOF ;
    public final lunegrammarParser.top_return top() throws RecognitionException {
        lunegrammarParser.top_return retval = new lunegrammarParser.top_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token EOF2=null;
        lunegrammarParser.def_return def1 =null;


        CommonTree EOF2_tree=null;

        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:12:4: ( ( def )+ EOF )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:12:6: ( def )+ EOF
            {
            root_0 = (CommonTree)adaptor.nil();


            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:12:6: ( def )+
            int cnt1=0;
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==19) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:12:7: def
            	    {
            	    pushFollow(FOLLOW_def_in_top39);
            	    def1=def();

            	    state._fsp--;

            	    adaptor.addChild(root_0, def1.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt1 >= 1 ) break loop1;
                        EarlyExitException eee =
                            new EarlyExitException(1, input);
                        throw eee;
                }
                cnt1++;
            } while (true);


            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_top43); 
            EOF2_tree = 
            (CommonTree)adaptor.create(EOF2)
            ;
            adaptor.addChild(root_0, EOF2_tree);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "top"


    public static class expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "expr"
    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:13:1: expr : ( app -> app | let -> let | lambda -> lambda | ifxp -> ifxp );
    public final lunegrammarParser.expr_return expr() throws RecognitionException {
        lunegrammarParser.expr_return retval = new lunegrammarParser.expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        lunegrammarParser.app_return app3 =null;

        lunegrammarParser.let_return let4 =null;

        lunegrammarParser.lambda_return lambda5 =null;

        lunegrammarParser.ifxp_return ifxp6 =null;


        RewriteRuleSubtreeStream stream_lambda=new RewriteRuleSubtreeStream(adaptor,"rule lambda");
        RewriteRuleSubtreeStream stream_app=new RewriteRuleSubtreeStream(adaptor,"rule app");
        RewriteRuleSubtreeStream stream_let=new RewriteRuleSubtreeStream(adaptor,"rule let");
        RewriteRuleSubtreeStream stream_ifxp=new RewriteRuleSubtreeStream(adaptor,"rule ifxp");
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:13:5: ( app -> app | let -> let | lambda -> lambda | ifxp -> ifxp )
            int alt2=4;
            switch ( input.LA(1) ) {
            case EQ:
            case FLOAT:
            case ID:
            case INT:
            case STRING:
            case 16:
                {
                alt2=1;
                }
                break;
            case 24:
                {
                alt2=2;
                }
                break;
            case 21:
                {
                alt2=3;
                }
                break;
            case 22:
                {
                alt2=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;

            }

            switch (alt2) {
                case 1 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:13:7: app
                    {
                    pushFollow(FOLLOW_app_in_expr49);
                    app3=app();

                    state._fsp--;

                    stream_app.add(app3.getTree());

                    // AST REWRITE
                    // elements: app
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 13:11: -> app
                    {
                        adaptor.addChild(root_0, stream_app.nextTree());

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:14:11: let
                    {
                    pushFollow(FOLLOW_let_in_expr65);
                    let4=let();

                    state._fsp--;

                    stream_let.add(let4.getTree());

                    // AST REWRITE
                    // elements: let
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 14:15: -> let
                    {
                        adaptor.addChild(root_0, stream_let.nextTree());

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 3 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:15:11: lambda
                    {
                    pushFollow(FOLLOW_lambda_in_expr81);
                    lambda5=lambda();

                    state._fsp--;

                    stream_lambda.add(lambda5.getTree());

                    // AST REWRITE
                    // elements: lambda
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 15:18: -> lambda
                    {
                        adaptor.addChild(root_0, stream_lambda.nextTree());

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 4 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:16:11: ifxp
                    {
                    pushFollow(FOLLOW_ifxp_in_expr98);
                    ifxp6=ifxp();

                    state._fsp--;

                    stream_ifxp.add(ifxp6.getTree());

                    // AST REWRITE
                    // elements: ifxp
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 16:16: -> ifxp
                    {
                        adaptor.addChild(root_0, stream_ifxp.nextTree());

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "expr"


    public static class app_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "app"
    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:17:1: app : ( p_expr )+ ;
    public final lunegrammarParser.app_return app() throws RecognitionException {
        lunegrammarParser.app_return retval = new lunegrammarParser.app_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        lunegrammarParser.p_expr_return p_expr7 =null;



        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:17:4: ( ( p_expr )+ )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:17:6: ( p_expr )+
            {
            root_0 = (CommonTree)adaptor.nil();


            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:17:6: ( p_expr )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==EQ||LA3_0==FLOAT||(LA3_0 >= ID && LA3_0 <= INT)||LA3_0==STRING||LA3_0==16) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:17:7: p_expr
            	    {
            	    pushFollow(FOLLOW_p_expr_in_app109);
            	    p_expr7=p_expr();

            	    state._fsp--;

            	    adaptor.addChild(root_0, p_expr7.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "app"


    public static class p_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "p_expr"
    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:18:1: p_expr : ( basic_expr -> basic_expr | paren_expr -> paren_expr );
    public final lunegrammarParser.p_expr_return p_expr() throws RecognitionException {
        lunegrammarParser.p_expr_return retval = new lunegrammarParser.p_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        lunegrammarParser.basic_expr_return basic_expr8 =null;

        lunegrammarParser.paren_expr_return paren_expr9 =null;


        RewriteRuleSubtreeStream stream_paren_expr=new RewriteRuleSubtreeStream(adaptor,"rule paren_expr");
        RewriteRuleSubtreeStream stream_basic_expr=new RewriteRuleSubtreeStream(adaptor,"rule basic_expr");
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:18:7: ( basic_expr -> basic_expr | paren_expr -> paren_expr )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==EQ||LA4_0==FLOAT||(LA4_0 >= ID && LA4_0 <= INT)||LA4_0==STRING) ) {
                alt4=1;
            }
            else if ( (LA4_0==16) ) {
                alt4=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;

            }
            switch (alt4) {
                case 1 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:18:9: basic_expr
                    {
                    pushFollow(FOLLOW_basic_expr_in_p_expr117);
                    basic_expr8=basic_expr();

                    state._fsp--;

                    stream_basic_expr.add(basic_expr8.getTree());

                    // AST REWRITE
                    // elements: basic_expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 18:20: -> basic_expr
                    {
                        adaptor.addChild(root_0, stream_basic_expr.nextTree());

                    }


                    retval.tree = root_0;

                    }
                    break;
                case 2 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:19:15: paren_expr
                    {
                    pushFollow(FOLLOW_paren_expr_in_p_expr137);
                    paren_expr9=paren_expr();

                    state._fsp--;

                    stream_paren_expr.add(paren_expr9.getTree());

                    // AST REWRITE
                    // elements: paren_expr
                    // token labels: 
                    // rule labels: retval
                    // token list labels: 
                    // rule list labels: 
                    // wildcard labels: 
                    retval.tree = root_0;
                    RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

                    root_0 = (CommonTree)adaptor.nil();
                    // 19:26: -> paren_expr
                    {
                        adaptor.addChild(root_0, stream_paren_expr.nextTree());

                    }


                    retval.tree = root_0;

                    }
                    break;

            }
            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "p_expr"


    public static class basic_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "basic_expr"
    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:20:1: basic_expr : ( ID | EQ | INT | FLOAT | STRING );
    public final lunegrammarParser.basic_expr_return basic_expr() throws RecognitionException {
        lunegrammarParser.basic_expr_return retval = new lunegrammarParser.basic_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token set10=null;

        CommonTree set10_tree=null;

        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:20:11: ( ID | EQ | INT | FLOAT | STRING )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:
            {
            root_0 = (CommonTree)adaptor.nil();


            set10=(Token)input.LT(1);

            if ( input.LA(1)==EQ||input.LA(1)==FLOAT||(input.LA(1) >= ID && input.LA(1) <= INT)||input.LA(1)==STRING ) {
                input.consume();
                adaptor.addChild(root_0, 
                (CommonTree)adaptor.create(set10)
                );
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "basic_expr"


    public static class paren_expr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "paren_expr"
    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:21:1: paren_expr : '(' expr ')' -> expr ;
    public final lunegrammarParser.paren_expr_return paren_expr() throws RecognitionException {
        lunegrammarParser.paren_expr_return retval = new lunegrammarParser.paren_expr_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token char_literal11=null;
        Token char_literal13=null;
        lunegrammarParser.expr_return expr12 =null;


        CommonTree char_literal11_tree=null;
        CommonTree char_literal13_tree=null;
        RewriteRuleTokenStream stream_17=new RewriteRuleTokenStream(adaptor,"token 17");
        RewriteRuleTokenStream stream_16=new RewriteRuleTokenStream(adaptor,"token 16");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:21:11: ( '(' expr ')' -> expr )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:21:13: '(' expr ')'
            {
            char_literal11=(Token)match(input,16,FOLLOW_16_in_paren_expr169);  
            stream_16.add(char_literal11);


            pushFollow(FOLLOW_expr_in_paren_expr171);
            expr12=expr();

            state._fsp--;

            stream_expr.add(expr12.getTree());

            char_literal13=(Token)match(input,17,FOLLOW_17_in_paren_expr173);  
            stream_17.add(char_literal13);


            // AST REWRITE
            // elements: expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 21:26: -> expr
            {
                adaptor.addChild(root_0, stream_expr.nextTree());

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "paren_expr"


    public static class let_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "let"
    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:22:1: let : 'let' ( ID )+ EQ e1= expr 'in' e2= expr -> ^( 'let' ( ID )+ $e1 $e2) ;
    public final lunegrammarParser.let_return let() throws RecognitionException {
        lunegrammarParser.let_return retval = new lunegrammarParser.let_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal14=null;
        Token ID15=null;
        Token EQ16=null;
        Token string_literal17=null;
        lunegrammarParser.expr_return e1 =null;

        lunegrammarParser.expr_return e2 =null;


        CommonTree string_literal14_tree=null;
        CommonTree ID15_tree=null;
        CommonTree EQ16_tree=null;
        CommonTree string_literal17_tree=null;
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_23=new RewriteRuleTokenStream(adaptor,"token 23");
        RewriteRuleTokenStream stream_24=new RewriteRuleTokenStream(adaptor,"token 24");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:22:4: ( 'let' ( ID )+ EQ e1= expr 'in' e2= expr -> ^( 'let' ( ID )+ $e1 $e2) )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:22:6: 'let' ( ID )+ EQ e1= expr 'in' e2= expr
            {
            string_literal14=(Token)match(input,24,FOLLOW_24_in_let183);  
            stream_24.add(string_literal14);


            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:22:12: ( ID )+
            int cnt5=0;
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ID) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:22:13: ID
            	    {
            	    ID15=(Token)match(input,ID,FOLLOW_ID_in_let186);  
            	    stream_ID.add(ID15);


            	    }
            	    break;

            	default :
            	    if ( cnt5 >= 1 ) break loop5;
                        EarlyExitException eee =
                            new EarlyExitException(5, input);
                        throw eee;
                }
                cnt5++;
            } while (true);


            EQ16=(Token)match(input,EQ,FOLLOW_EQ_in_let190);  
            stream_EQ.add(EQ16);


            pushFollow(FOLLOW_expr_in_let194);
            e1=expr();

            state._fsp--;

            stream_expr.add(e1.getTree());

            string_literal17=(Token)match(input,23,FOLLOW_23_in_let196);  
            stream_23.add(string_literal17);


            pushFollow(FOLLOW_expr_in_let200);
            e2=expr();

            state._fsp--;

            stream_expr.add(e2.getTree());

            // AST REWRITE
            // elements: 24, ID, e2, e1
            // token labels: 
            // rule labels: retval, e1, e2
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_e1=new RewriteRuleSubtreeStream(adaptor,"rule e1",e1!=null?e1.tree:null);
            RewriteRuleSubtreeStream stream_e2=new RewriteRuleSubtreeStream(adaptor,"rule e2",e2!=null?e2.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 22:42: -> ^( 'let' ( ID )+ $e1 $e2)
            {
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:22:45: ^( 'let' ( ID )+ $e1 $e2)
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                stream_24.nextNode()
                , root_1);

                if ( !(stream_ID.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_ID.hasNext() ) {
                    adaptor.addChild(root_1, 
                    stream_ID.nextNode()
                    );

                }
                stream_ID.reset();

                adaptor.addChild(root_1, stream_e1.nextTree());

                adaptor.addChild(root_1, stream_e2.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "let"


    public static class def_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "def"
    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:23:1: def : 'def' ( ID )+ EQ expr -> ^( 'def' ( ID )+ expr ) ;
    public final lunegrammarParser.def_return def() throws RecognitionException {
        lunegrammarParser.def_return retval = new lunegrammarParser.def_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal18=null;
        Token ID19=null;
        Token EQ20=null;
        lunegrammarParser.expr_return expr21 =null;


        CommonTree string_literal18_tree=null;
        CommonTree ID19_tree=null;
        CommonTree EQ20_tree=null;
        RewriteRuleTokenStream stream_19=new RewriteRuleTokenStream(adaptor,"token 19");
        RewriteRuleTokenStream stream_EQ=new RewriteRuleTokenStream(adaptor,"token EQ");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:23:4: ( 'def' ( ID )+ EQ expr -> ^( 'def' ( ID )+ expr ) )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:23:6: 'def' ( ID )+ EQ expr
            {
            string_literal18=(Token)match(input,19,FOLLOW_19_in_def223);  
            stream_19.add(string_literal18);


            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:23:12: ( ID )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==ID) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:23:13: ID
            	    {
            	    ID19=(Token)match(input,ID,FOLLOW_ID_in_def226);  
            	    stream_ID.add(ID19);


            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);


            EQ20=(Token)match(input,EQ,FOLLOW_EQ_in_def230);  
            stream_EQ.add(EQ20);


            pushFollow(FOLLOW_expr_in_def232);
            expr21=expr();

            state._fsp--;

            stream_expr.add(expr21.getTree());

            // AST REWRITE
            // elements: expr, 19, ID
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 23:26: -> ^( 'def' ( ID )+ expr )
            {
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:23:29: ^( 'def' ( ID )+ expr )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                stream_19.nextNode()
                , root_1);

                if ( !(stream_ID.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_ID.hasNext() ) {
                    adaptor.addChild(root_1, 
                    stream_ID.nextNode()
                    );

                }
                stream_ID.reset();

                adaptor.addChild(root_1, stream_expr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "def"


    public static class lambda_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "lambda"
    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:24:1: lambda : 'fun' ( ID )+ '->' expr -> ^( 'fun' ( ID )+ expr ) ;
    public final lunegrammarParser.lambda_return lambda() throws RecognitionException {
        lunegrammarParser.lambda_return retval = new lunegrammarParser.lambda_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal22=null;
        Token ID23=null;
        Token string_literal24=null;
        lunegrammarParser.expr_return expr25 =null;


        CommonTree string_literal22_tree=null;
        CommonTree ID23_tree=null;
        CommonTree string_literal24_tree=null;
        RewriteRuleTokenStream stream_21=new RewriteRuleTokenStream(adaptor,"token 21");
        RewriteRuleTokenStream stream_ID=new RewriteRuleTokenStream(adaptor,"token ID");
        RewriteRuleTokenStream stream_18=new RewriteRuleTokenStream(adaptor,"token 18");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:24:7: ( 'fun' ( ID )+ '->' expr -> ^( 'fun' ( ID )+ expr ) )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:24:9: 'fun' ( ID )+ '->' expr
            {
            string_literal22=(Token)match(input,21,FOLLOW_21_in_lambda251);  
            stream_21.add(string_literal22);


            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:24:15: ( ID )+
            int cnt7=0;
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==ID) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:24:16: ID
            	    {
            	    ID23=(Token)match(input,ID,FOLLOW_ID_in_lambda254);  
            	    stream_ID.add(ID23);


            	    }
            	    break;

            	default :
            	    if ( cnt7 >= 1 ) break loop7;
                        EarlyExitException eee =
                            new EarlyExitException(7, input);
                        throw eee;
                }
                cnt7++;
            } while (true);


            string_literal24=(Token)match(input,18,FOLLOW_18_in_lambda258);  
            stream_18.add(string_literal24);


            pushFollow(FOLLOW_expr_in_lambda260);
            expr25=expr();

            state._fsp--;

            stream_expr.add(expr25.getTree());

            // AST REWRITE
            // elements: 21, ID, expr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 24:31: -> ^( 'fun' ( ID )+ expr )
            {
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:24:34: ^( 'fun' ( ID )+ expr )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                stream_21.nextNode()
                , root_1);

                if ( !(stream_ID.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_ID.hasNext() ) {
                    adaptor.addChild(root_1, 
                    stream_ID.nextNode()
                    );

                }
                stream_ID.reset();

                adaptor.addChild(root_1, stream_expr.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "lambda"


    public static class ifxp_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };


    // $ANTLR start "ifxp"
    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:25:1: ifxp : 'if' cond= expr 'then' body= expr 'else' alt= expr -> ^( 'if' $cond $body $alt) ;
    public final lunegrammarParser.ifxp_return ifxp() throws RecognitionException {
        lunegrammarParser.ifxp_return retval = new lunegrammarParser.ifxp_return();
        retval.start = input.LT(1);


        CommonTree root_0 = null;

        Token string_literal26=null;
        Token string_literal27=null;
        Token string_literal28=null;
        lunegrammarParser.expr_return cond =null;

        lunegrammarParser.expr_return body =null;

        lunegrammarParser.expr_return alt =null;


        CommonTree string_literal26_tree=null;
        CommonTree string_literal27_tree=null;
        CommonTree string_literal28_tree=null;
        RewriteRuleTokenStream stream_20=new RewriteRuleTokenStream(adaptor,"token 20");
        RewriteRuleTokenStream stream_22=new RewriteRuleTokenStream(adaptor,"token 22");
        RewriteRuleTokenStream stream_25=new RewriteRuleTokenStream(adaptor,"token 25");
        RewriteRuleSubtreeStream stream_expr=new RewriteRuleSubtreeStream(adaptor,"rule expr");
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:25:5: ( 'if' cond= expr 'then' body= expr 'else' alt= expr -> ^( 'if' $cond $body $alt) )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:25:7: 'if' cond= expr 'then' body= expr 'else' alt= expr
            {
            string_literal26=(Token)match(input,22,FOLLOW_22_in_ifxp279);  
            stream_22.add(string_literal26);


            pushFollow(FOLLOW_expr_in_ifxp283);
            cond=expr();

            state._fsp--;

            stream_expr.add(cond.getTree());

            string_literal27=(Token)match(input,25,FOLLOW_25_in_ifxp285);  
            stream_25.add(string_literal27);


            pushFollow(FOLLOW_expr_in_ifxp289);
            body=expr();

            state._fsp--;

            stream_expr.add(body.getTree());

            string_literal28=(Token)match(input,20,FOLLOW_20_in_ifxp291);  
            stream_20.add(string_literal28);


            pushFollow(FOLLOW_expr_in_ifxp295);
            alt=expr();

            state._fsp--;

            stream_expr.add(alt.getTree());

            // AST REWRITE
            // elements: 22, alt, body, cond
            // token labels: 
            // rule labels: body, retval, alt, cond
            // token list labels: 
            // rule list labels: 
            // wildcard labels: 
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_body=new RewriteRuleSubtreeStream(adaptor,"rule body",body!=null?body.tree:null);
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"rule retval",retval!=null?retval.tree:null);
            RewriteRuleSubtreeStream stream_alt=new RewriteRuleSubtreeStream(adaptor,"rule alt",alt!=null?alt.tree:null);
            RewriteRuleSubtreeStream stream_cond=new RewriteRuleSubtreeStream(adaptor,"rule cond",cond!=null?cond.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 25:55: -> ^( 'if' $cond $body $alt)
            {
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:25:58: ^( 'if' $cond $body $alt)
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot(
                stream_22.nextNode()
                , root_1);

                adaptor.addChild(root_1, stream_cond.nextTree());

                adaptor.addChild(root_1, stream_body.nextTree());

                adaptor.addChild(root_1, stream_alt.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }


            retval.tree = root_0;

            }

            retval.stop = input.LT(-1);


            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);

        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }

        finally {
        	// do for sure before leaving
        }
        return retval;
    }
    // $ANTLR end "ifxp"

    // Delegated rules


 

    public static final BitSet FOLLOW_def_in_top39 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_EOF_in_top43 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_app_in_expr49 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_let_in_expr65 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lambda_in_expr81 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifxp_in_expr98 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_p_expr_in_app109 = new BitSet(new long[]{0x0000000000012D22L});
    public static final BitSet FOLLOW_basic_expr_in_p_expr117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_paren_expr_in_p_expr137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_16_in_paren_expr169 = new BitSet(new long[]{0x0000000001612D20L});
    public static final BitSet FOLLOW_expr_in_paren_expr171 = new BitSet(new long[]{0x0000000000020000L});
    public static final BitSet FOLLOW_17_in_paren_expr173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_24_in_let183 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_let186 = new BitSet(new long[]{0x0000000000000420L});
    public static final BitSet FOLLOW_EQ_in_let190 = new BitSet(new long[]{0x0000000001612D20L});
    public static final BitSet FOLLOW_expr_in_let194 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_23_in_let196 = new BitSet(new long[]{0x0000000001612D20L});
    public static final BitSet FOLLOW_expr_in_let200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_19_in_def223 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_def226 = new BitSet(new long[]{0x0000000000000420L});
    public static final BitSet FOLLOW_EQ_in_def230 = new BitSet(new long[]{0x0000000001612D20L});
    public static final BitSet FOLLOW_expr_in_def232 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_21_in_lambda251 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_ID_in_lambda254 = new BitSet(new long[]{0x0000000000040400L});
    public static final BitSet FOLLOW_18_in_lambda258 = new BitSet(new long[]{0x0000000001612D20L});
    public static final BitSet FOLLOW_expr_in_lambda260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_22_in_ifxp279 = new BitSet(new long[]{0x0000000001612D20L});
    public static final BitSet FOLLOW_expr_in_ifxp283 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_25_in_ifxp285 = new BitSet(new long[]{0x0000000001612D20L});
    public static final BitSet FOLLOW_expr_in_ifxp289 = new BitSet(new long[]{0x0000000000100000L});
    public static final BitSet FOLLOW_20_in_ifxp291 = new BitSet(new long[]{0x0000000001612D20L});
    public static final BitSet FOLLOW_expr_in_ifxp295 = new BitSet(new long[]{0x0000000000000002L});

}
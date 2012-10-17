// $ANTLR 3.4 /home/raph/Projects/lune/antlr-grammar/lunegrammar.g 2012-10-17 17:16:29
package main.java.lexparse;

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked"})
public class lunegrammarLexer extends Lexer {
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
    // delegators
    public Lexer[] getDelegates() {
        return new Lexer[] {};
    }

    public lunegrammarLexer() {} 
    public lunegrammarLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public lunegrammarLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
    }
    public String getGrammarFileName() { return "/home/raph/Projects/lune/antlr-grammar/lunegrammar.g"; }

    // $ANTLR start "T__16"
    public final void mT__16() throws RecognitionException {
        try {
            int _type = T__16;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:2:7: ( '(' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:2:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__16"

    // $ANTLR start "T__17"
    public final void mT__17() throws RecognitionException {
        try {
            int _type = T__17;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:3:7: ( ')' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:3:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__17"

    // $ANTLR start "T__18"
    public final void mT__18() throws RecognitionException {
        try {
            int _type = T__18;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:4:7: ( '->' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:4:9: '->'
            {
            match("->"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__18"

    // $ANTLR start "T__19"
    public final void mT__19() throws RecognitionException {
        try {
            int _type = T__19;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:5:7: ( 'def' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:5:9: 'def'
            {
            match("def"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__19"

    // $ANTLR start "T__20"
    public final void mT__20() throws RecognitionException {
        try {
            int _type = T__20;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:6:7: ( 'else' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:6:9: 'else'
            {
            match("else"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__20"

    // $ANTLR start "T__21"
    public final void mT__21() throws RecognitionException {
        try {
            int _type = T__21;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:7:7: ( 'fun' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:7:9: 'fun'
            {
            match("fun"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__21"

    // $ANTLR start "T__22"
    public final void mT__22() throws RecognitionException {
        try {
            int _type = T__22;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:8:7: ( 'if' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:8:9: 'if'
            {
            match("if"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__22"

    // $ANTLR start "T__23"
    public final void mT__23() throws RecognitionException {
        try {
            int _type = T__23;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:9:7: ( 'in' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:9:9: 'in'
            {
            match("in"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__23"

    // $ANTLR start "T__24"
    public final void mT__24() throws RecognitionException {
        try {
            int _type = T__24;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:10:7: ( 'let' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:10:9: 'let'
            {
            match("let"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__24"

    // $ANTLR start "T__25"
    public final void mT__25() throws RecognitionException {
        try {
            int _type = T__25;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:11:7: ( 'then' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:11:9: 'then'
            {
            match("then"); 



            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "T__25"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:27:3: ( ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '+' | '-' | '/' | '*' | '|' | '<' | '>' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '+' | '-' | '/' | '*' | '|' | '<' | '>' )* )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:27:5: ( 'a' .. 'z' | 'A' .. 'Z' | '_' | '+' | '-' | '/' | '*' | '|' | '<' | '>' ) ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '+' | '-' | '/' | '*' | '|' | '<' | '>' )*
            {
            if ( (input.LA(1) >= '*' && input.LA(1) <= '+')||input.LA(1)=='-'||input.LA(1)=='/'||input.LA(1)=='<'||input.LA(1)=='>'||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||input.LA(1)=='|' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:27:57: ( 'a' .. 'z' | 'A' .. 'Z' | '0' .. '9' | '_' | '+' | '-' | '/' | '*' | '|' | '<' | '>' )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( ((LA1_0 >= '*' && LA1_0 <= '+')||LA1_0=='-'||(LA1_0 >= '/' && LA1_0 <= '9')||LA1_0=='<'||LA1_0=='>'||(LA1_0 >= 'A' && LA1_0 <= 'Z')||LA1_0=='_'||(LA1_0 >= 'a' && LA1_0 <= 'z')||LA1_0=='|') ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:
            	    {
            	    if ( (input.LA(1) >= '*' && input.LA(1) <= '+')||input.LA(1)=='-'||(input.LA(1) >= '/' && input.LA(1) <= '9')||input.LA(1)=='<'||input.LA(1)=='>'||(input.LA(1) >= 'A' && input.LA(1) <= 'Z')||input.LA(1)=='_'||(input.LA(1) >= 'a' && input.LA(1) <= 'z')||input.LA(1)=='|' ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "EQ"
    public final void mEQ() throws RecognitionException {
        try {
            int _type = EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:28:3: ( '=' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:28:5: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EQ"

    // $ANTLR start "INT"
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:31:5: ( ( '0' .. '9' )+ )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:31:7: ( '0' .. '9' )+
            {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:31:7: ( '0' .. '9' )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0 >= '0' && LA2_0 <= '9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "INT"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:35:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT )
            int alt9=3;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:35:9: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )?
                    {
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:35:9: ( '0' .. '9' )+
                    int cnt3=0;
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0 >= '0' && LA3_0 <= '9')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


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


                    match('.'); 

                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:35:25: ( '0' .. '9' )*
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0 >= '0' && LA4_0 <= '9')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop4;
                        }
                    } while (true);


                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:35:37: ( EXPONENT )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='E'||LA5_0=='e') ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:35:37: EXPONENT
                            {
                            mEXPONENT(); 


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:36:9: '.' ( '0' .. '9' )+ ( EXPONENT )?
                    {
                    match('.'); 

                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:36:13: ( '0' .. '9' )+
                    int cnt6=0;
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0 >= '0' && LA6_0 <= '9')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


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


                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:36:25: ( EXPONENT )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='E'||LA7_0=='e') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:36:25: EXPONENT
                            {
                            mEXPONENT(); 


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:37:9: ( '0' .. '9' )+ EXPONENT
                    {
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:37:9: ( '0' .. '9' )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0 >= '0' && LA8_0 <= '9')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:
                    	    {
                    	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt8 >= 1 ) break loop8;
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);


                    mEXPONENT(); 


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "COMMENT"
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:41:5: ( '--' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' | '(*' ( options {greedy=false; } : . )* '*)' )
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='-') ) {
                alt13=1;
            }
            else if ( (LA13_0=='(') ) {
                alt13=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;

            }
            switch (alt13) {
                case 1 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:41:9: '--' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
                    {
                    match("--"); 



                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:41:14: (~ ( '\\n' | '\\r' ) )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0 >= '\u0000' && LA10_0 <= '\t')||(LA10_0 >= '\u000B' && LA10_0 <= '\f')||(LA10_0 >= '\u000E' && LA10_0 <= '\uFFFF')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:
                    	    {
                    	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '\t')||(input.LA(1) >= '\u000B' && input.LA(1) <= '\f')||(input.LA(1) >= '\u000E' && input.LA(1) <= '\uFFFF') ) {
                    	        input.consume();
                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;
                    	    }


                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);


                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:41:28: ( '\\r' )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0=='\r') ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:41:28: '\\r'
                            {
                            match('\r'); 

                            }
                            break;

                    }


                    match('\n'); 

                    _channel=HIDDEN;

                    }
                    break;
                case 2 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:42:9: '(*' ( options {greedy=false; } : . )* '*)'
                    {
                    match("(*"); 



                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:42:14: ( options {greedy=false; } : . )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( (LA12_0=='*') ) {
                            int LA12_1 = input.LA(2);

                            if ( (LA12_1==')') ) {
                                alt12=2;
                            }
                            else if ( ((LA12_1 >= '\u0000' && LA12_1 <= '(')||(LA12_1 >= '*' && LA12_1 <= '\uFFFF')) ) {
                                alt12=1;
                            }


                        }
                        else if ( ((LA12_0 >= '\u0000' && LA12_0 <= ')')||(LA12_0 >= '+' && LA12_0 <= '\uFFFF')) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:42:42: .
                    	    {
                    	    matchAny(); 

                    	    }
                    	    break;

                    	default :
                    	    break loop12;
                        }
                    } while (true);


                    match("*)"); 



                    _channel=HIDDEN;

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "COMMENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:45:5: ( ( ' ' | '\\t' | '\\r' | '\\n' ) )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:45:9: ( ' ' | '\\t' | '\\r' | '\\n' )
            {
            if ( (input.LA(1) >= '\t' && input.LA(1) <= '\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "WS"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:53:5: ( '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"' )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:53:8: '\"' ( ESC_SEQ |~ ( '\\\\' | '\"' ) )* '\"'
            {
            match('\"'); 

            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:53:12: ( ESC_SEQ |~ ( '\\\\' | '\"' ) )*
            loop14:
            do {
                int alt14=3;
                int LA14_0 = input.LA(1);

                if ( (LA14_0=='\\') ) {
                    alt14=1;
                }
                else if ( ((LA14_0 >= '\u0000' && LA14_0 <= '!')||(LA14_0 >= '#' && LA14_0 <= '[')||(LA14_0 >= ']' && LA14_0 <= '\uFFFF')) ) {
                    alt14=2;
                }


                switch (alt14) {
            	case 1 :
            	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:53:14: ESC_SEQ
            	    {
            	    mESC_SEQ(); 


            	    }
            	    break;
            	case 2 :
            	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:53:24: ~ ( '\\\\' | '\"' )
            	    {
            	    if ( (input.LA(1) >= '\u0000' && input.LA(1) <= '!')||(input.LA(1) >= '#' && input.LA(1) <= '[')||(input.LA(1) >= ']' && input.LA(1) <= '\uFFFF') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop14;
                }
            } while (true);


            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:58:10: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:58:12: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:58:22: ( '+' | '-' )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0=='+'||LA15_0=='-') ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }


            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:58:33: ( '0' .. '9' )+
            int cnt16=0;
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( ((LA16_0 >= '0' && LA16_0 <= '9')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:
            	    {
            	    if ( (input.LA(1) >= '0' && input.LA(1) <= '9') ) {
            	        input.consume();
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt16 >= 1 ) break loop16;
                        EarlyExitException eee =
                            new EarlyExitException(16, input);
                        throw eee;
                }
                cnt16++;
            } while (true);


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "EXPONENT"

    // $ANTLR start "HEX_DIGIT"
    public final void mHEX_DIGIT() throws RecognitionException {
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:61:11: ( ( '0' .. '9' | 'a' .. 'f' | 'A' .. 'F' ) )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:
            {
            if ( (input.LA(1) >= '0' && input.LA(1) <= '9')||(input.LA(1) >= 'A' && input.LA(1) <= 'F')||(input.LA(1) >= 'a' && input.LA(1) <= 'f') ) {
                input.consume();
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;
            }


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "HEX_DIGIT"

    // $ANTLR start "ESC_SEQ"
    public final void mESC_SEQ() throws RecognitionException {
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:65:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) | UNICODE_ESC | OCTAL_ESC )
            int alt17=3;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='\\') ) {
                switch ( input.LA(2) ) {
                case '\"':
                case '\'':
                case '\\':
                case 'b':
                case 'f':
                case 'n':
                case 'r':
                case 't':
                    {
                    alt17=1;
                    }
                    break;
                case 'u':
                    {
                    alt17=2;
                    }
                    break;
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                    {
                    alt17=3;
                    }
                    break;
                default:
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 1, input);

                    throw nvae;

                }

            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;

            }
            switch (alt17) {
                case 1 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:65:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
                    {
                    match('\\'); 

                    if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:66:9: UNICODE_ESC
                    {
                    mUNICODE_ESC(); 


                    }
                    break;
                case 3 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:67:9: OCTAL_ESC
                    {
                    mOCTAL_ESC(); 


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "ESC_SEQ"

    // $ANTLR start "OCTAL_ESC"
    public final void mOCTAL_ESC() throws RecognitionException {
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:72:5: ( '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) ( '0' .. '7' ) | '\\\\' ( '0' .. '7' ) )
            int alt18=3;
            int LA18_0 = input.LA(1);

            if ( (LA18_0=='\\') ) {
                int LA18_1 = input.LA(2);

                if ( ((LA18_1 >= '0' && LA18_1 <= '3')) ) {
                    int LA18_2 = input.LA(3);

                    if ( ((LA18_2 >= '0' && LA18_2 <= '7')) ) {
                        int LA18_4 = input.LA(4);

                        if ( ((LA18_4 >= '0' && LA18_4 <= '7')) ) {
                            alt18=1;
                        }
                        else {
                            alt18=2;
                        }
                    }
                    else {
                        alt18=3;
                    }
                }
                else if ( ((LA18_1 >= '4' && LA18_1 <= '7')) ) {
                    int LA18_3 = input.LA(3);

                    if ( ((LA18_3 >= '0' && LA18_3 <= '7')) ) {
                        alt18=2;
                    }
                    else {
                        alt18=3;
                    }
                }
                else {
                    NoViableAltException nvae =
                        new NoViableAltException("", 18, 1, input);

                    throw nvae;

                }
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;

            }
            switch (alt18) {
                case 1 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:72:9: '\\\\' ( '0' .. '3' ) ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '3') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 2 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:73:9: '\\\\' ( '0' .. '7' ) ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;
                case 3 :
                    // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:74:9: '\\\\' ( '0' .. '7' )
                    {
                    match('\\'); 

                    if ( (input.LA(1) >= '0' && input.LA(1) <= '7') ) {
                        input.consume();
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;
                    }


                    }
                    break;

            }

        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "OCTAL_ESC"

    // $ANTLR start "UNICODE_ESC"
    public final void mUNICODE_ESC() throws RecognitionException {
        try {
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:79:5: ( '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT )
            // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:79:9: '\\\\' 'u' HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
            {
            match('\\'); 

            match('u'); 

            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            mHEX_DIGIT(); 


            }


        }
        finally {
        	// do for sure before leaving
        }
    }
    // $ANTLR end "UNICODE_ESC"

    public void mTokens() throws RecognitionException {
        // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:8: ( T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | ID | EQ | INT | FLOAT | COMMENT | WS | STRING )
        int alt19=17;
        alt19 = dfa19.predict(input);
        switch (alt19) {
            case 1 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:10: T__16
                {
                mT__16(); 


                }
                break;
            case 2 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:16: T__17
                {
                mT__17(); 


                }
                break;
            case 3 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:22: T__18
                {
                mT__18(); 


                }
                break;
            case 4 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:28: T__19
                {
                mT__19(); 


                }
                break;
            case 5 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:34: T__20
                {
                mT__20(); 


                }
                break;
            case 6 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:40: T__21
                {
                mT__21(); 


                }
                break;
            case 7 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:46: T__22
                {
                mT__22(); 


                }
                break;
            case 8 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:52: T__23
                {
                mT__23(); 


                }
                break;
            case 9 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:58: T__24
                {
                mT__24(); 


                }
                break;
            case 10 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:64: T__25
                {
                mT__25(); 


                }
                break;
            case 11 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:70: ID
                {
                mID(); 


                }
                break;
            case 12 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:73: EQ
                {
                mEQ(); 


                }
                break;
            case 13 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:76: INT
                {
                mINT(); 


                }
                break;
            case 14 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:80: FLOAT
                {
                mFLOAT(); 


                }
                break;
            case 15 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:86: COMMENT
                {
                mCOMMENT(); 


                }
                break;
            case 16 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:94: WS
                {
                mWS(); 


                }
                break;
            case 17 :
                // /home/raph/Projects/lune/antlr-grammar/lunegrammar.g:1:97: STRING
                {
                mSTRING(); 


                }
                break;

        }

    }


    protected DFA9 dfa9 = new DFA9(this);
    protected DFA19 dfa19 = new DFA19(this);
    static final String DFA9_eotS =
        "\5\uffff";
    static final String DFA9_eofS =
        "\5\uffff";
    static final String DFA9_minS =
        "\2\56\3\uffff";
    static final String DFA9_maxS =
        "\1\71\1\145\3\uffff";
    static final String DFA9_acceptS =
        "\2\uffff\1\2\1\1\1\3";
    static final String DFA9_specialS =
        "\5\uffff}>";
    static final String[] DFA9_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\3\1\uffff\12\1\13\uffff\1\4\37\uffff\1\4",
            "",
            "",
            ""
    };

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "34:1: FLOAT : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT );";
        }
    }
    static final String DFA19_eotS =
        "\1\uffff\1\21\1\uffff\7\12\2\uffff\1\33\5\uffff\1\34\4\12\1\41\1"+
        "\42\2\12\2\uffff\1\12\1\45\1\12\1\47\2\uffff\1\50\1\12\1\uffff\1"+
        "\52\2\uffff\1\53\2\uffff";
    static final String DFA19_eofS =
        "\54\uffff";
    static final String DFA19_minS =
        "\1\11\1\52\1\uffff\1\55\1\145\1\154\1\165\1\146\1\145\1\150\2\uffff"+
        "\1\56\5\uffff\1\52\1\0\1\146\1\163\1\156\2\52\1\164\1\145\2\uffff"+
        "\1\0\1\52\1\145\1\52\2\uffff\1\52\1\156\1\uffff\1\52\2\uffff\1\52"+
        "\2\uffff";
    static final String DFA19_maxS =
        "\1\174\1\52\1\uffff\1\76\1\145\1\154\1\165\1\156\1\145\1\150\2\uffff"+
        "\1\145\5\uffff\1\174\1\uffff\1\146\1\163\1\156\2\174\1\164\1\145"+
        "\2\uffff\1\uffff\1\174\1\145\1\174\2\uffff\1\174\1\156\1\uffff\1"+
        "\174\2\uffff\1\174\2\uffff";
    static final String DFA19_acceptS =
        "\2\uffff\1\2\7\uffff\1\13\1\14\1\uffff\1\16\1\20\1\21\1\17\1\1\11"+
        "\uffff\1\15\1\3\4\uffff\1\7\1\10\2\uffff\1\4\1\uffff\1\6\1\11\1"+
        "\uffff\1\5\1\12";
    static final String DFA19_specialS =
        "\23\uffff\1\0\11\uffff\1\1\16\uffff}>";
    static final String[] DFA19_transitionS = {
            "\2\16\2\uffff\1\16\22\uffff\1\16\1\uffff\1\17\5\uffff\1\1\1"+
            "\2\2\12\1\uffff\1\3\1\15\1\12\12\14\2\uffff\1\12\1\13\1\12\2"+
            "\uffff\32\12\4\uffff\1\12\1\uffff\3\12\1\4\1\5\1\6\2\12\1\7"+
            "\2\12\1\10\7\12\1\11\6\12\1\uffff\1\12",
            "\1\20",
            "",
            "\1\23\20\uffff\1\22",
            "\1\24",
            "\1\25",
            "\1\26",
            "\1\27\7\uffff\1\30",
            "\1\31",
            "\1\32",
            "",
            "",
            "\1\15\1\uffff\12\14\13\uffff\1\15\37\uffff\1\15",
            "",
            "",
            "",
            "",
            "",
            "\2\12\1\uffff\1\12\1\uffff\13\12\2\uffff\1\12\1\uffff\1\12"+
            "\2\uffff\32\12\4\uffff\1\12\1\uffff\32\12\1\uffff\1\12",
            "\52\20\2\35\1\20\1\35\1\20\13\35\2\20\1\35\1\20\1\35\2\20\32"+
            "\35\4\20\1\35\1\20\32\35\1\20\1\35\uff83\20",
            "\1\36",
            "\1\37",
            "\1\40",
            "\2\12\1\uffff\1\12\1\uffff\13\12\2\uffff\1\12\1\uffff\1\12"+
            "\2\uffff\32\12\4\uffff\1\12\1\uffff\32\12\1\uffff\1\12",
            "\2\12\1\uffff\1\12\1\uffff\13\12\2\uffff\1\12\1\uffff\1\12"+
            "\2\uffff\32\12\4\uffff\1\12\1\uffff\32\12\1\uffff\1\12",
            "\1\43",
            "\1\44",
            "",
            "",
            "\52\20\2\35\1\20\1\35\1\20\13\35\2\20\1\35\1\20\1\35\2\20\32"+
            "\35\4\20\1\35\1\20\32\35\1\20\1\35\uff83\20",
            "\2\12\1\uffff\1\12\1\uffff\13\12\2\uffff\1\12\1\uffff\1\12"+
            "\2\uffff\32\12\4\uffff\1\12\1\uffff\32\12\1\uffff\1\12",
            "\1\46",
            "\2\12\1\uffff\1\12\1\uffff\13\12\2\uffff\1\12\1\uffff\1\12"+
            "\2\uffff\32\12\4\uffff\1\12\1\uffff\32\12\1\uffff\1\12",
            "",
            "",
            "\2\12\1\uffff\1\12\1\uffff\13\12\2\uffff\1\12\1\uffff\1\12"+
            "\2\uffff\32\12\4\uffff\1\12\1\uffff\32\12\1\uffff\1\12",
            "\1\51",
            "",
            "\2\12\1\uffff\1\12\1\uffff\13\12\2\uffff\1\12\1\uffff\1\12"+
            "\2\uffff\32\12\4\uffff\1\12\1\uffff\32\12\1\uffff\1\12",
            "",
            "",
            "\2\12\1\uffff\1\12\1\uffff\13\12\2\uffff\1\12\1\uffff\1\12"+
            "\2\uffff\32\12\4\uffff\1\12\1\uffff\32\12\1\uffff\1\12",
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__16 | T__17 | T__18 | T__19 | T__20 | T__21 | T__22 | T__23 | T__24 | T__25 | ID | EQ | INT | FLOAT | COMMENT | WS | STRING );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA19_19 = input.LA(1);

                        s = -1;
                        if ( ((LA19_19 >= '*' && LA19_19 <= '+')||LA19_19=='-'||(LA19_19 >= '/' && LA19_19 <= '9')||LA19_19=='<'||LA19_19=='>'||(LA19_19 >= 'A' && LA19_19 <= 'Z')||LA19_19=='_'||(LA19_19 >= 'a' && LA19_19 <= 'z')||LA19_19=='|') ) {s = 29;}

                        else if ( ((LA19_19 >= '\u0000' && LA19_19 <= ')')||LA19_19==','||LA19_19=='.'||(LA19_19 >= ':' && LA19_19 <= ';')||LA19_19=='='||(LA19_19 >= '?' && LA19_19 <= '@')||(LA19_19 >= '[' && LA19_19 <= '^')||LA19_19=='`'||LA19_19=='{'||(LA19_19 >= '}' && LA19_19 <= '\uFFFF')) ) {s = 16;}

                        else s = 10;

                        if ( s>=0 ) return s;
                        break;

                    case 1 : 
                        int LA19_29 = input.LA(1);

                        s = -1;
                        if ( ((LA19_29 >= '\u0000' && LA19_29 <= ')')||LA19_29==','||LA19_29=='.'||(LA19_29 >= ':' && LA19_29 <= ';')||LA19_29=='='||(LA19_29 >= '?' && LA19_29 <= '@')||(LA19_29 >= '[' && LA19_29 <= '^')||LA19_29=='`'||LA19_29=='{'||(LA19_29 >= '}' && LA19_29 <= '\uFFFF')) ) {s = 16;}

                        else if ( ((LA19_29 >= '*' && LA19_29 <= '+')||LA19_29=='-'||(LA19_29 >= '/' && LA19_29 <= '9')||LA19_29=='<'||LA19_29=='>'||(LA19_29 >= 'A' && LA19_29 <= 'Z')||LA19_29=='_'||(LA19_29 >= 'a' && LA19_29 <= 'z')||LA19_29=='|') ) {s = 29;}

                        else s = 10;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 19, _s, input);
            error(nvae);
            throw nvae;
        }

    }
 

}
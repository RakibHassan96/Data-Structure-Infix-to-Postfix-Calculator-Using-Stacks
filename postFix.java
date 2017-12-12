import java.io.*;

class postFix
	{
	public static void main(String[] args) throws IOException
		{
		String var1;
		String var2;
		String infix;
		String postfix;
		int output;
		
		while(true) 
			{
			var1 = getString();
			if( var1.equals("") )
				break;
			ParsePost aParser = new ParsePost(var1);
			int a = aParser.ParseVariable();
			
			var2 = getString();
			if( var2.equals("") )
				break;
			ParsePost bParser = new ParsePost(var2);
			int b = bParser.ParseVariable();
			
			infix = getString();
			if( infix.equals("") )
				break;
			InToPost theTrans = new InToPost(infix);
			postfix = theTrans.doTrans();
			ParsePost yParser = new ParsePost(postfix);
			output = yParser.doParse(a, b);
			
			System.out.println("Test of Returning Input Values: A = " + a + ", B = " + b);
			System.out.println("Y = " + output);
			
		}
	}
	//--------------------------------------------------------------
	public static String getString() throws IOException 
		{
		InputStreamReader isr = new InputStreamReader(System.in); 
		BufferedReader br = new BufferedReader(isr);
		String s = br.readLine();
		return s;
		} 
	//--------------------------------------------------------------
	}

///////////////////////////////////////////////////////////////////////

class StackInfix
	{
	private int maxSize; 
	private char[] stackArray; 
	private int top;
	//-------------------------------------------------------------- 
	public StackInfix(int s)
		{
		maxSize = s;
		stackArray = new char[maxSize]; 
		top = -1;
		}
	//-------------------------------------------------------------- 
	public void push(char j)
		{ stackArray[++top] = j; } 
	//--------------------------------------------------------------
	public char pop()
		{ return stackArray[top--]; } 
	//--------------------------------------------------------------
	public char peek()
		{ return stackArray[top]; }
	//-------------------------------------------------------------- 
	public boolean isEmpty()
		{ return (top == -1); } 
	//--------------------------------------------------------------
	public int size()
		{ return top+1; }
	//-------------------------------------------------------------- 
	public char peekN(int n)
		{ return stackArray[n]; } 
	//--------------------------------------------------------------
	public void displayStack(String s) 
		{
		System.out.print(s); 
		System.out.print("Stack (bottom-->top): "); 
		for(int j=0; j<size(); j++)
			{
			System.out.print( peekN(j) ); 
			System.out.print(' ');
			}
		System.out.println("");
		}
	//--------------------------------------------------------------
	}

////////////////////////////////////////////////////////////////////

class StackX
	{
	private int maxSize; 
	private int[] stackArray; 
	private int top;
	//-------------------------------------------------------------- 
	public StackX(int size)
		{
		maxSize = size;
		stackArray = new int[maxSize]; top = -1;
		}
	//-------------------------------------------------------------- 
	public void push(int j)
		{ stackArray[++top] = j; } 
	//--------------------------------------------------------------
	public int pop()
		{ return stackArray[top--]; }
	//-------------------------------------------------------------- 
	public int peek()
		{ return stackArray[top]; } 
	//--------------------------------------------------------------
	public boolean isEmpty()
		{ return (top == -1); }
	//-------------------------------------------------------------- 
	public boolean isFull()
		{ return (top == maxSize-1); } 
	//--------------------------------------------------------------
	public int size()
		{ return top+1; }
	//-------------------------------------------------------------- 
	public int peekN(int n)
		{ return stackArray[n]; } 
	//--------------------------------------------------------------
	public void displayStack(String s) 
		{
		System.out.print(s); 
		System.out.print("Stack (bottom-->top): "); 
		for(int j=0; j<size(); j++)
			{
			System.out.print( peekN(j) ); 
			System.out.print(' ');
			}
		System.out.println("");
		} 
	//--------------------------------------------------------------
	} 

/////////////////////////////////////////////////////////////////////

class InToPost
	{
	private StackInfix theStack; 
	private String input; 
	private String output = "";
	//-------------------------------------------------------------- 
	public InToPost(String in)
		{
		input = in;
		int stackSize = input.length(); 
		theStack = new StackInfix(stackSize); 
		}
	//--------------------------------------------------------------
	public String doTrans()
		{
		for(int j=0; j<input.length(); j++) 
			{
			char ch = input.charAt(j); 
			switch(ch)
				{
				case '+': 
				case '-':
					gotOper(ch, 1);
					break; 
				case '*': 
				case '/':
					gotOper(ch, 2);
					break; 
				case '(':
					theStack.push(ch);
					break; 
				case ')':
					gotParen(ch);
					break;
				default:
					output = output + ch;
					break;
				}
			}
		while( !theStack.isEmpty() )
			{
			output = output + theStack.pop();
			}
		return output;
		}
	//-------------------------------------------------------------- 
	public void gotOper(char opThis, int prec1)
		{ 
		while( !theStack.isEmpty() )
			{
			char opTop = theStack.pop();
			if( opTop == '(' ) 
				{
				theStack.push(opTop); 
				break;
				}
			else 
				{
				int prec2;
				if(opTop=='+' || opTop=='-') 
					prec2 = 1;
				else
					prec2 = 2;
				if(prec2 < prec1) 
					{
					theStack.push(opTop); 
					break;
					}
				else
					output = output + opTop; 
				}
			}
		theStack.push(opThis);
		}
	//--------------------------------------------------------------
	public void gotParen(char ch)
		{
		while( !theStack.isEmpty() )
			{
			char chx = theStack.pop();
			if( chx == '(' )
				break;
			else
				output = output + chx;
			}
		}
	}

/////////////////////////////////////////////////////////////////////


class ParsePost
	{
	private StackX theStack; 
	private String input;
	//-------------------------------------------------------------- 
	public ParsePost(String s)
		{ input = s; } 
	//--------------------------------------------------------------
	public int ParseVariable() 
		{
		theStack = new StackX(5); 
		char ch;
		int j;
		int varX = 0;
		for(j=0; j<input.length(); j++) 
			{
			ch = input.charAt(j);  
			if (ch >= '0' && ch <= '9')
				{
				varX = ch - '0';
				}
			}
		return varX;
		}
	//--------------------------------------------------------------
	public int doParse(int a, int b) 
		{
		theStack = new StackX(20); 
		char ch;
		int j;
		int num1, num2, interAns;
		for(j=0; j<input.length(); j++) 
			{
			ch = input.charAt(j);
			if (ch == 'a' || ch == 'A')
				theStack.push( a );
			else if (ch == 'b' || ch == 'B')
				theStack.push( b );
			else if(ch >= '0' && ch <= '9')
				theStack.push( (int)(ch-'0') );
			else if(ch == 'y' || ch == 'Y' || ch == '=' || ch == ' ')
				{}
			else
				{
				num2 = theStack.pop(); 
				num1 = theStack.pop(); 
				switch(ch)
					{
					case '+':
						interAns = num1 + num2;
					break; 
					case '-':
						interAns = num1 - num2;
						break; 
					case '*':
						interAns = num1 * num2;
						break; 
					case '/':
						interAns = num1 / num2;
						break; 
					default:
						interAns = 0;
					}
				theStack.push(interAns);
				}
			}
		interAns = theStack.pop(); 
		return interAns;
		}
	}

// END OF APP
package Calculator;

public class Calculator
{
    public static void main(String[] args)
    {
        System.out.println("Calculator module. Please run CalculatorReceiver and CalculatorSender separately.");
    }

    public double add(double a, double b)
    {
        return a + b;
    }

    public double subtract(double a, double b)
    {
        return a - b;
    }

    public double multiply(double a, double b)
    {
        return a * b;
    }

    public double divide(double a, double b)
    {
        if (b == 0)
        {
            // Handle division by zero
            if (a != 0)
            {
                if (a > 0)
                {
                    return Double.POSITIVE_INFINITY;
                }
                else
                {
                    return Double.NEGATIVE_INFINITY;
                }
            }
            
            // Indeterminate form 0/0
            return Double.NaN;
        }
        
        // Normal division
        return a / b;
    }
}

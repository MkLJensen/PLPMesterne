object FigurTegnerenScala {
    def sum(a:Int, b:Int): Int = {
      if(a>b)
        a
      else
        b
        //f(a)+sum(f, a+1, b)
    }

    def fact(n:Int): Int = {
      if (n == 1) 1
      else n * fact(n-1)
    }

    //println(sum((x:Int) => 2*x, 1, 10))
}

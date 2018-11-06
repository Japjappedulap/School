import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.*;

public class Main {
    private static void Sum(int N, int M) {
        Matrix x = new Matrix(N, M, true);
        Matrix y = new Matrix(N, M, true);

        System.out.println("Begin classic sum");
        double start_time = System.currentTimeMillis();
        Matrix sum = x.sum(y);
        double current_time = System.currentTimeMillis();
        System.out.println("Finished classic sum, took: " + Double.toString((current_time - start_time) / 1000) + "s" + ", begin thread pool");


        start_time = System.currentTimeMillis();
        Matrix threadPoolSum = x.sumThreadPool(y);
        current_time = System.currentTimeMillis();
        System.out.println("Finished thread pool sum, took: " + Double.toString((current_time - start_time) / 1000) + "s" + ", begin futures");


        start_time = System.currentTimeMillis();
        Matrix futuresSum = x.sumFutures(y);
        current_time = System.currentTimeMillis();
        System.out.println("Finished futures sum, took: " + Double.toString((current_time - start_time) / 1000) + "s");


        // check for it to be the same
        assert(sum.isEqual(threadPoolSum));
        assert(sum.isEqual(futuresSum));
    }

    private static void Prod(int N, int M, boolean withAssert) {
        Matrix x = new Matrix(N, M, true);
        Matrix y = new Matrix(N, M, true);

        if (!withAssert) {
            System.out.println("Begin thread pool prod");
            double start_time = System.currentTimeMillis();
            Matrix threadPoolProd = x.prodThreadPool(y);
            double current_time = System.currentTimeMillis();
            System.out.println("Finished thread pool prod, took: " + Double.toString((current_time - start_time) / 1000) + "s" + ", begin futures");

            start_time = System.currentTimeMillis();
            Matrix futuresProd = x.sumFutures(y);
            current_time = System.currentTimeMillis();
            System.out.println("Finished futures prod, took: " + Double.toString((current_time - start_time) / 1000) + "s");
            return;
        }

        System.out.println("Begin classic prod");
        double start_time = System.currentTimeMillis();
        Matrix prod = x.prod(y);
        double current_time = System.currentTimeMillis();
        System.out.println("Finished classic prod, took: " + Double.toString((current_time - start_time) / 1000) + "s" + ", begin thread pool");


        start_time = System.currentTimeMillis();
        Matrix threadPoolProd = x.prodThreadPool(y);
        current_time = System.currentTimeMillis();
        System.out.println("Finished thread pool prod, took: " + Double.toString((current_time - start_time) / 1000) + "s" + ", begin futures");


        start_time = System.currentTimeMillis();
        Matrix futuresProd = x.sumFutures(y);
        current_time = System.currentTimeMillis();
        System.out.println("Finished futures prod, took: " + Double.toString((current_time - start_time) / 1000) + "s");

        // check for it to be the same
        assert(prod.isEqual(threadPoolProd));
        assert(prod.isEqual(futuresProd));
    }

    public static void main(String[] args) {
//        TestCaseProd();
        TestCaseSum();

    }
    private static void TestCaseProd() {
        int[] N = {1000, 1000, 1000, 3000, 3000};
        int[] M = {1000, 1000, 1000, 3000, 3000};
        int[] ThrNumber = {1, 8, 200, 8, 200};
        for (int i = 0; i < 5; ++i) {
            System.err.println("Begin test with preset: N = " + N[i] + " M = " + M[i] + " Threads = " + ThrNumber[i]);
            Matrix.numberOfThreads = ThrNumber[i];
            if (N[i] > 1000) {
                Prod(N[i], M[i], false);
            } else {
                Prod(N[i], M[i], true);
            }
        }
    }

    private static void TestCaseSum() {
        int[] N = {3000, 4000, 5000};
        int[] M = {3000, 4000, 5000};
        int[] ThrNumber = {1, 8, 200};
        for (int i = 0; i < 3; ++i) {
            System.err.println("Begin test with preset: N = " + N[i] + " M = " + M[i] + " Threads = " + ThrNumber[i]);
            Matrix.numberOfThreads = ThrNumber[i];
            Sum(N[i], M[i]);
        }
    }
}

class Matrix {
    private int data [][];
    private int N, M;

    private static int getRandom(int L, int R) {
        return (int) (Math.random()*((R-L)+1))+L;
    }
    static int numberOfThreads = 8;

    Matrix(int N, int M, boolean generate) {
        data = new int [N][M];
        this.N = N;
        this.M = M;
        if (generate) {
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < M; ++j) {
                    data[i][j] = getRandom(2, 10000000);
                }
            }
        }
    }

    void setData(int i, int j, int val) {
        this.data[i][j] = val;
    }

    int getData(int i, int j) {
        return this.data[i][j];
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < N; ++i){
            for (int j = 0; j < M; ++j)
                res.append(Integer.toString(data[i][j])).append(' ');
            res.append('\n');
        }
        return res.toString();
    }

    Matrix sumThreadPool(Matrix other) {
        Matrix result = new Matrix(N, M, false);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                threadPoolExecutor.submit(new MakeSum(new Op(i, j, this, other, result)));
            }
        }
        threadPoolExecutor.shutdown();
        return result;
    }

    Matrix sum(Matrix other) {
        Matrix result = new Matrix(N, M, false);
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                result.setData(i, j, this.data[i][j] + other.getData(i, j));

            }
        }
        return result;
    }

    Matrix sumFutures(Matrix other) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        Matrix result = new Matrix(N, M, false);
        Collection<Future<Op2> > futures = new LinkedList<>();

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                futures.add(executor.submit(new GetSum(new Op2(i, j, this, other))));
            }
        }
        executor.shutdown();

        FinishFutures(result, futures);

        return result;
    }

    private void FinishFutures(Matrix result, Collection<Future<Op2>> futures) {
        for (Future<Op2> future : futures) {
            try {
                Op2 computed_result = future.get();
                result.setData(computed_result.i, computed_result.j, computed_result.res);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    boolean isEqual(Matrix other) {
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < M; ++j)
                if (this.data[i][j] != other.getData(i, j))
                    return false;
        return true;
    }

    private static int getProduct(final int i, final int j, final Matrix A, final Matrix B) {
        int result = 0;
        for (int k = 0; k < A.M; k++) {
            result += A.data[i][k] * B.data[k][j];
        }
        return result;
    }

    Matrix prod(Matrix other) {
        Matrix result = new Matrix(N, M, false);
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                result.setData(i, j, getProduct(i, j, this, other));
            }
        }
        return result;
    }

    Matrix prodThreadPool(Matrix other) {
        Matrix result = new Matrix(N, M, false);
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                threadPoolExecutor.submit(new MakeProd(new Op(i, j, this, other, result)));
            }
        }
        threadPoolExecutor.shutdown();
        return result;
    }

    Matrix prodFutures(Matrix other) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        Matrix result = new Matrix(N, M, false);
        Collection<Future<Op2> > futures = new LinkedList<>();

        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < M; ++j) {
                futures.add(executor.submit(new GetProd(new Op2(i, j, this, other))));
            }
        }
        executor.shutdown();

        FinishFutures(result, (Collection<Future<Op2>>) futures);

        return result;
    }

    int getM() {
        return M;
    }
}

class Op {
    int i, j;
    final Matrix A, B;
    Matrix res;
    Op(int i, int j, Matrix A, Matrix B, Matrix res) {
        this.i = i;
        this.j = j;
        this.A = A;
        this.B = B;
        this.res = res;
    }
}

class Op2 {
    int i, j;
    final Matrix A, B;
    int res;
    Op2(int i, int j, Matrix A, Matrix B) {
        this.i = i;
        this.j = j;
        this.A = A;
        this.B = B;
    }
}


class MakeSum implements Callable<Op> {
    private Op op;
    MakeSum(Op op) {
        this.op = op;
    }

    @Override
    public Op call() {
        op.res.setData(op.i, op.j, op.A.getData(op.i, op.j) + op.B.getData(op.i, op.j));
        return this.op;
    }
}

class GetSum implements Callable<Op2> {
    private Op2 op;
    GetSum(Op2 op) {
        this.op = op;
    }

    @Override
    public Op2 call() {
        op.res = op.A.getData(op.i, op.j) + op.B.getData(op.i, op.j);
        return this.op;
    }
}

class MakeProd implements Callable<Op> {
    private Op op;
    MakeProd(Op op) {
        this.op = op;
    }

    @Override
    public Op call() {
        int accumulated = 0;
        final int K = op.A.getM();
        for (int k = 0; k < K; ++k) {
            accumulated += (op.A.getData(op.i, k) * op.A.getData(k, op.j));
        }

        op.res.setData(op.i, op.j, accumulated);
        return this.op;
    }
}

class GetProd implements Callable<Op2> {
    private Op2 op;
    GetProd(Op2 op) {
        this.op = op;
    }

    @Override
    public Op2 call() {
        int accumulated = 0;
        final int K = op.A.getM();
        for (int k = 0; k < K; ++k) {
            accumulated += (op.A.getData(op.i, k) * op.A.getData(k, op.j));
        }
        op.res = accumulated;
        return this.op;
    }
}
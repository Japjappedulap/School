## TestCaseProd

    Begin test with preset: N = 1000 M = 1000 Threads = 1
    Begin classic prod
    Finished classic prod, took: 2.325s, begin thread pool
    Finished thread pool prod, took: 0.4s, begin futures
    Finished futures prod, took: 0.418s
    Begin test with preset: N = 1000 M = 1000 Threads = 8
    Begin classic prod
    Finished classic prod, took: 2.374s, begin thread pool
    Finished thread pool prod, took: 0.301s, begin futures
    Finished futures prod, took: 0.597s
    Begin test with preset: N = 1000 M = 1000 Threads = 200
    Begin classic prod
    Finished classic prod, took: 3.085s, begin thread pool
    Finished thread pool prod, took: 0.235s, begin futures
    Begin test with preset: N = 3000 M = 3000 Threads = 8
    Finished futures prod, took: 0.698s
    Begin classic prod
    Finished classic prod, took: 243.104s, begin thread pool
    Finished thread pool prod, took: 4.08s, begin futures
    Begin test with preset: N = 3000 M = 3000 Threads = 200
    Finished futures prod, took: 4.727s
    Begin classic prod
    Finished classic prod, took: 282.289s, begin thread pool
    Finished thread pool prod, took: 6.669s, begin futures
    Finished futures prod, took: 5.373s
    
    Process finished with exit code 0

## TestCaseSum

    /usr/lib/jvm/default-java/bin/java -javaagent:/snap/intellij-idea-ultimate/86/lib/idea_rt.jar=44433:/snap/intellij-idea-ultimate/86/bin -Dfile.encoding=UTF-8 -classpath "/home/potra/Desktop/School/School/Parallel and distributed programming/Lab3/out/production/Lab3" Main
    Begin test with preset: N = 3000 M = 3000 Threads = 1
    Begin classic sum
    Finished classic sum, took: 0.047s, begin thread pool
    Finished thread pool sum, took: 2.87s, begin futures
    Finished futures sum, took: 4.024s
    Begin test with preset: N = 4000 M = 4000 Threads = 8
    Begin classic sum
    Finished classic sum, took: 0.114s, begin thread pool
    Finished thread pool sum, took: 8.154s, begin futures
    Finished futures sum, took: 8.245s
    Begin test with preset: N = 5000 M = 5000 Threads = 200
    Begin classic sum
    Finished classic sum, took: 0.041s, begin thread pool
    Finished thread pool sum, took: 12.939s, begin futures
    Finished futures sum, took: 15.016s
    
    Process finished with exit code 0

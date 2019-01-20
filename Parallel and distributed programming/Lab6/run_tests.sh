echo "Compiling classic algorithm - classic_1.cpp'"
g++ -O2 -std=c++11 -pthread -Dhome -Wall classic_1.cpp -o classic_1.o
echo "Compiling classic algorithm (threaded) - classic_1.cpp'"
g++ -O2 -std=c++11 -pthread -Dhome -Wall classic_2.cpp -o classic_2.o
echo "Compiling karasuba algorithm - karatsuba_1.cpp'"
g++ -O2 -std=c++11 -pthread -Dhome -Wall karatsuba_1.cpp -o karatsuba_1.o
echo "Compiling karasuba algorithm (threaded) - karatsuba_1.cpp'"
g++ -O2 -std=c++11 -pthread -Dhome -Wall karatsuba_2.cpp -o karatsuba_2.o

echo ""

for i in `ls tests/*.in`
do
  echo "Running test $i"
  head -n1 $i
  ./classic_1.o $i
  ./classic_2.o $i
  ./karatsuba_1.o $i
  ./karatsuba_2.o $i
  echo ""
done

rm *.o

#include <iostream>
#include <thread>
#include <vector>
#include <mutex>
#include <atomic>
#include <future>
#include "util.hpp"
#include "Matrix.hpp"
using namespace std;

class BigInt{
public:
    vector<int> content;

    BigInt() {}

    explicit BigInt(const string& number) {
        for (auto rit = number.rbegin(); rit != number.rend(); ++rit) {
            content.push_back(*rit - '0');
        }
    }

    BigInt operator*(const BigInt& other) {
        BigInt result;
        for (int index = 0, transport = 0; index < max(this->content.size(), other.content.size()) || transport; transport /= 10, ++index) {
            result.content.push_back(0);
            result.content[index] += (transport % 10);
            for (int i = 0; i <= index && i < this->content.size() && index-i < other.content.size(); ++i) {
                int temp = this->content[i] * other.content[index-i];
                transport += temp / 10;
                result.content[index] += temp % 10;
            }
        }
        return result;
    }

    void pp() {
        for (auto rit = content.rbegin(); rit != content.rend(); ++rit) {
            cout << *rit;
        }
        cout << '\n';
    }
};

int main() {
    BigInt x("10");
    BigInt y("10");
    BigInt z = x * y;
    z.pp();
    return 0;
}




//
//     1234 *
//     5678
//-----------
//     9872 +
//    8638-
//   7404--
//  6170---
//-----------
//  7006652
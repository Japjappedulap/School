//
// Created by potra on 16.10.2018.
//

#ifndef LAB2_MATRIX_H
#define LAB2_MATRIX_H

#include <fstream>
#include <vector>
#include <iostream>

class Matrix {
private:
    std::vector<std::vector<long long> > data;
    int N{}, M{};
public:
    explicit Matrix(const std::string &);

    explicit Matrix(int, int);

    explicit Matrix();

    Matrix(const Matrix &other);

    void pp();

    void logToFile(const std::string &);

    void log();

    int getN() const;

    int getM() const;

    void setData(int, int, long long);

    long long getData(int, int) const;

    Matrix operator+(const Matrix &);

    Matrix operator*(const Matrix &);

    void randomize(int, int);
};

#endif //LAB2_MATRIX_H

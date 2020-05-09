package com.mycompany.serviceimpl;

import com.mycompany.service.HighSchoolMathService;

public class HighSchoolMathServiceImpl implements HighSchoolMathService {
    @Override
    public int sub(int a, int b) {
        return a - b;
    }
}

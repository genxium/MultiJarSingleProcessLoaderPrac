package com.mycompany.serviceimpl;

import com.mycompany.service.HighSchoolMathService;
import org.springframework.stereotype.Service;

//@Service("highSchoolMathServiceImpl")
@Service // Implicitly names the service "highSchoolMathServiceImpl".
public class HighSchoolMathServiceImpl implements HighSchoolMathService {
    @Override
    public int sub(int a, int b) {
        return a - b;
    }
}

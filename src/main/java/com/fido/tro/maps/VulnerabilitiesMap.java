package com.fido.tro.maps;

import com.fido.tro.vulnerabilities.Vulnerability;
import com.fido.tro.vulnerabilities.XSS;

import java.util.HashMap;
import java.util.Map;

public class VulnerabilitiesMap {
    Map<String, Vulnerability> list = new HashMap<>();

    public VulnerabilitiesMap() {
        list.put("xss", new XSS());
    }
}

package com.university.demo.python.TransferPython;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ToPython {

    @Autowired
    private RunPython runPython;

    // python脚本路径
    @Value("${config.python-script-path}")
    String pythonScriptPath;

    public String wordcloud(String contents) {
        String path = pythonScriptPath + "simple_linaer_regaression.py";
        return runPython.runPython(contents, path);
    }

    public String wordcloud2(String contents) {
        String path = pythonScriptPath + "wordcloud2.py";
        return runPython.runPython(contents, path);
    }

    public String itemrec(String contents) {
        String path = pythonScriptPath + "rec.py";
        return runPython.runPython(contents, path);
    }

    public String userrec(String contents) {
        String path = pythonScriptPath + "UserCF.py";
        return runPython.runPython(contents, path);
    }

    public String mlp(String contents) {
        String path = pythonScriptPath + "mlp.py";
        return runPython.runPython(contents, path);
    }

    public String lstm(String contents) {
        String path = pythonScriptPath + "lstm.py";
        return runPython.runPython(contents, path);
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.opens.referentiel.creator;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import static java.nio.charset.StandardCharsets.*;

/**
 *
 * @author alingua
 */
public class FileGenerator {

    private VelocityParametersContext vpc;
    
    public FileGenerator(String referentiel, String referentielLabel) {
        vpc = new VelocityParametersContext();
        vpc.setReferentiel(String.valueOf(referentiel.charAt(0)).toUpperCase()
                + referentiel.substring(1));
        vpc.setReferentielLabel(referentielLabel);
    }

    protected File getSqlFile(String destinationFolder) {
        return new File(destinationFolder
                + "/src/main/resources/sql/"
                + vpc.getReferentiel().toLowerCase()
                + "-insert.sql");
    }

    protected File getI18nDefaultFile(String destinationFolder, String category) {
        return new File(destinationFolder
                + "/src/main/resources/i18n/"
                + category + "-"
                + vpc.getReferentiel().replace(".", "").toLowerCase()
                + "-I18N.properties");
    }

    protected File getI18nFile(String destinationFolder, String lang, String category) {
        return new File(destinationFolder
                + "/src/main/resources/i18n/"
                + category + "-"
                + vpc.getReferentiel().replace(".", "").toLowerCase()
                + "-I18N_" + lang.toLowerCase() + ".properties");
    }

    public VelocityContext getContextRuleClassFile(
            String referentielLower, String packageName, String test,
            String testLabel, VelocityContext context) throws IOException {
        String[] testCodeArray = test.split("-");
        vpc.getThemes().add(Integer.valueOf(testCodeArray[0]));

        vpc.setTestCode(test);
        vpc.setPackageString(packageName);
        String[] twoDigitTestCode = normalize2Digits(testCodeArray);
        vpc.setClassString(vpc.getReferentiel().replace(".", "") + "Rule"
                + twoDigitTestCode[0] + twoDigitTestCode[1] + twoDigitTestCode[2]);
        context.put("referentiel", vpc.getReferentiel().replace(".", ""));
        context.put("referentielFolder", vpc.getReferentiel());
        context.put("rule", vpc.getClassString());
        context.put("ruleCode", vpc.getTestCode());
        context.put("referentielName", vpc.getReferentielLabel());
        context.put("testLabel", testLabel);
        context.put("package", vpc.getPackageString());
        return context;
    }

    private String[] normalize2Digits(String[] testCodeArray) {
        String thematique = testCodeArray[0];
        String critereCode = testCodeArray[1];
        String test2DigitsCode = testCodeArray[2];
        if (testCodeArray[0].length() == 1) {
            thematique = "0" + testCodeArray[0];
        }
        if (testCodeArray[1].length() == 1) {
            critereCode = "0" + testCodeArray[1];
        }
        if (testCodeArray[2].length() == 1) {
            test2DigitsCode = "0" + testCodeArray[2];
        }
        String[] classCode = {thematique, critereCode, test2DigitsCode};
        return classCode;
    }

    public void writeFileCodeGenerate(VelocityContext context, Template temp,
            String destinationFolder) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        vpc.getClassRule().add(vpc.getClassString());
        File classFile = new File(destinationFolder
                + "/src/main/java/"
                + vpc.getPackageString().replace('.', '/') + "/"
                + vpc.getReferentiel().replace(".", "").toLowerCase() + "/"
                + vpc.getClassString() + ".java");
        FileUtils.writeStringToFile(classFile, wr.toString());
    }

    public void adaptParentPom(VelocityContext context, Template temp,
            String destinationFolder) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        File pomFile = new File(destinationFolder
                + "/pom.xml");
        FileUtils.writeStringToFile(pomFile, wr.toString());
    }

    public void adaptRefPom(VelocityContext context, Template temp,
            String destinationFolder) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        File refPomFile = new File(destinationFolder
                + "/pom.xml");
        FileUtils.writeStringToFile(refPomFile, wr.toString());
    }

    public void adaptTargzPom(VelocityContext context, Template temp,
            String destinationFolder) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        File refPomFile = new File(destinationFolder
                + "/pom.xml");
        FileUtils.writeStringToFile(refPomFile, wr.toString());
    }

    public void writeTestCaseGenerate(VelocityContext context, Template temp,
            String destinationFolder,
            String testCaseNumber) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        File testCaseFile = new File(destinationFolder
                + "/src/main/resources/testcases/"
                + vpc.getReferentiel().replace(".", "").toLowerCase() + "/"
                + vpc.getClassString() + "/" + vpc.getReferentiel().replace(".", "")
                + ".Test." + vpc.getTestCode().replace('-', '.')
                + "-" + testCaseNumber + context.get("state") + "-01.html");
        FileUtils.writeStringToFile(testCaseFile, wr.toString());
    }

    public void writeUnitTestGenerate(VelocityContext context, Template temp,
            String destinationFolder,
            String testCaseNumber) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        File testCaseFile = new File(destinationFolder
                + "/src/test/java/"
                + vpc.getPackageString().replace('.', '/') + "/"
                + vpc.getReferentiel().replace(".", "").toLowerCase() + "/"
                + vpc.getClassString() + "Test.java");
        FileUtils.writeStringToFile(testCaseFile, wr.toString());
    }

    public void writeRuleImplementationTestCaseGenerate(
            VelocityContext context, Template temp,
            String destinationFolder) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        File testCaseFile = new File(destinationFolder
                + "/src/test/java/"
                + vpc.getPackageString().replace('.', '/') + "/"
                + vpc.getReferentiel().replace(".", "").toLowerCase() + "/test/"
                + vpc.getReferentiel().replace(".", "")
                + "RuleImplementationTestCase.java");
        FileUtils.writeStringToFile(testCaseFile, wr.toString());
    }

    public void writei18NFile(String destinationFolder, Map categoryMap,
            String lang, String defaultLanguage,
            String category, String refDescriptor) throws IOException {
        if (category.equals("referential")) {
            writeI18NReferentialFile(destinationFolder, lang, defaultLanguage, category);
            return;
        }
        Object code = categoryMap.keySet().iterator().next();
        String desc = cleanI18NString(categoryMap.values().iterator().next().toString());
        StringBuilder sb = new StringBuilder();
        sb.append(vpc.getReferentiel().replace(".", ""));
        sb.append("-").append(code).append("=").append(desc).append("\n");
        if (category.equals("rule")) {
            writeTestUrlI18NFile(destinationFolder, refDescriptor, code, sb);
        }
        if (!FileUtils.readFileToString(getI18nFile(destinationFolder, lang, category), UTF_8).contains(sb.toString())) {
            FileUtils.writeStringToFile(FileUtils.getFile(getI18nFile(destinationFolder, lang, category)), sb.toString(), UTF_8, true);
        }
        if (lang.equalsIgnoreCase(defaultLanguage) && !FileUtils.readFileToString(getI18nDefaultFile(destinationFolder, category), UTF_8).contains(sb.toString())) {
            FileUtils.writeStringToFile(FileUtils.getFile(getI18nDefaultFile(destinationFolder, category)), sb.toString(), UTF_8, true);
        }
    }

    private String cleanI18NString(String desc) {
        if (desc.startsWith("\'")) {
            desc = desc.replaceFirst("\'", "");
        }
        if (desc.endsWith("\'")) {
            desc = desc.substring(0, desc.length() - 1);
        }
        return desc;
    }

    private StringBuilder writeTestUrlI18NFile(String destinationFolder, String refDescriptor, Object code, StringBuilder sb) {
        sb.append(vpc.getReferentiel().replace(".", ""));
        sb.append("-").append(code).append("-url=");
        if (StringUtils.isNotBlank(refDescriptor)) {
            sb.append(refDescriptor).append("#test-").append(code);
        } else {
            sb.append("#");
        }
        sb.append("\n");
        return sb;
    }

    private void writeI18NReferentialFile(String destinationFolder, String lang, String defaultLanguage, String category) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(vpc.getReferentiel().replace(".", "").toUpperCase()).append("=").append(vpc.getReferentielLabel()).append("\n");
        sb.append(vpc.getReferentiel().replace(".", "").toUpperCase()).append("-optgroup=").append(vpc.getReferentielLabel()).append("\n");
        sb.append(vpc.getReferentiel().replace(".", "").toUpperCase()).append("-LEVEL_1=A\n");
        sb.append(vpc.getReferentiel().replace(".", "").toUpperCase()).append("-LEVEL_2=AA\n");
        sb.append(vpc.getReferentiel().replace(".", "").toUpperCase()).append("-LEVEL_3=AAA");
        if (!FileUtils.readFileToString(getI18nFile(destinationFolder, lang, category), UTF_8).contains(sb.toString())) {
            FileUtils.writeStringToFile(FileUtils.getFile(getI18nFile(destinationFolder, lang, category)), sb.toString(), UTF_8, true);
        }
        if (lang.equalsIgnoreCase(defaultLanguage) && !FileUtils.readFileToString(getI18nDefaultFile(destinationFolder, category), UTF_8).contains(sb.toString())) {
            FileUtils.writeStringToFile(FileUtils.getFile(getI18nDefaultFile(destinationFolder, category)), sb.toString(), UTF_8, true);
        }
    }

    public void createI18NFiles(Set<String> langs, String destinationFolder) throws IOException {
        FileUtils.touch(getI18nDefaultFile(destinationFolder, "theme"));
        FileUtils.touch(getI18nDefaultFile(destinationFolder, "criterion"));
        FileUtils.touch(getI18nDefaultFile(destinationFolder, "rule"));
        FileUtils.touch(getI18nDefaultFile(destinationFolder, "rule-remark"));
        FileUtils.touch(getI18nDefaultFile(destinationFolder, "referential"));
        for (String lang : langs) {
            FileUtils.touch(getI18nFile(destinationFolder, lang, "theme"));
            FileUtils.touch(getI18nFile(destinationFolder, lang, "criterion"));
            FileUtils.touch(getI18nFile(destinationFolder, lang, "rule"));
            FileUtils.touch(getI18nFile(destinationFolder, lang, "rule-remark"));
            FileUtils.touch(getI18nFile(destinationFolder, lang, "referential"));
        }
    }

    public void writeDescriptorGenerate(VelocityContext context, Template temp,
            String destinationFolder) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        File descriptorFile = new File(destinationFolder
                + "/src/main/resources/"
                + "descriptor.xml");
        FileUtils.writeStringToFile(descriptorFile, wr.toString());
    }

    public void writeInstallGenerate(VelocityContext context, Template temp,
            String destinationFolder) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        File installFile = new File(destinationFolder
                + "/src/main/resources/"
                + "install.sh");
        FileUtils.writeStringToFile(installFile, wr.toString());
    }

    public void writeWebappBeansGenerate(VelocityContext context,
            Template temp, String destinationFolder) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        File beansWebappFile = new File(destinationFolder
                + "/src/main/resources/conf/context/"
                + vpc.getReferentiel().replace(".", "").toLowerCase()
                + "/web-app/"
                + vpc.getReferentiel().replace(".", "").toLowerCase()
                + "-beans-webapp.xml");
        FileUtils.writeStringToFile(beansWebappFile, wr.toString());
    }

    public void writeWebappBeansExpressionGenerate(VelocityContext context,
            Template temp, String destinationFolder) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        File beansWebappFile = new File(destinationFolder
                + "/src/main/resources/conf/context/"
                + vpc.getReferentiel().replace(".", "").toLowerCase()
                + "/web-app/export/" + "tgol-beans-"
                + vpc.getReferentiel().replace(".", "").toLowerCase()
                + "-expression.xml");
        FileUtils.writeStringToFile(beansWebappFile, wr.toString());
    }

    public void writeAuditResultConsoleBeanGenerate(VelocityContext context,
            Template temp, String destinationFolder) throws IOException {
        StringWriter wr = new StringWriter();
        context.put("themes", vpc.getThemes());
        temp.merge(context, wr);
        File beansAuditResultConsoleFile = new File(destinationFolder
                + "/src/main/resources/conf/context/"
                + vpc.getReferentiel().replace(".", "").toLowerCase()
                + "/web-app/mvc/form/" + "tgol-beans-"
                + vpc.getReferentiel().replace(".", "").toLowerCase()
                + "-audit-result-console-form.xml");
        FileUtils.writeStringToFile(beansAuditResultConsoleFile, wr.toString());
    }

    public void writeAuditSetUpFormBeanGenerate(VelocityContext context,
            Template temp, String destinationFolder) throws IOException {
        StringWriter wr = new StringWriter();
        temp.merge(context, wr);
        File beansAuditResultConsoleFile = new File(destinationFolder
                + "/src/main/resources/conf/context/"
                + vpc.getReferentiel().replace(".", "").toLowerCase()
                + "/web-app/mvc/form/" + "tgol-beans-"
                + vpc.getReferentiel().replace(".", "").toLowerCase()
                + "-audit-set-up-form.xml");
        FileUtils.writeStringToFile(beansAuditResultConsoleFile, wr.toString());
    }

    public void createSqlReference(String destinationFolder) throws IOException {
        FileUtils.touch(getSqlFile(destinationFolder));
        StringBuilder strb = new StringBuilder();
        strb.append("INSERT IGNORE INTO `REFERENCE` (`CD_REFERENCE`, `DESCRIPTION`, `LABEL`, `URL`, `RANK`, `ID_DEFAULT_LEVEL`) VALUES\n");
        strb.append("(\'").append(vpc.getReferentiel().replace(".", "").toUpperCase()).append("\', NULL, \'").append(vpc.getReferentielLabel()).append("\', \'\', 2000, 1);\n\n");
        strb.append("INSERT IGNORE INTO `TGSI_REFERENTIAL` (`Code`, `Label`) VALUES\n");
        strb.append("(\'").append(vpc.getReferentiel().replace(".", "").toUpperCase()).append("\', \'").append(vpc.getReferentielLabel()).append("\');\n\n");
        FileUtils.writeStringToFile(FileUtils.getFile(getSqlFile(destinationFolder)), strb.toString(), true);
    }

    public void createSqlTheme(String i18NPath, String destinationFolder) throws IOException {
        List<String> themesList = FileUtils.readLines(getI18nDefaultFile(i18NPath, "theme"));
        StringBuilder strb = new StringBuilder();
        strb.append("INSERT IGNORE INTO `THEME` (`CD_THEME`, `DESCRIPTION`, `LABEL`, `RANK`) VALUES\n");
//        FileUtils.writeStringToFile(FileUtils.getFile(getSqlFile(destinationFolder)), themeTable, true);
        for (int i = 0; i < themesList.size(); i++) {
            strb.append("(\'").append(themesList.get(i).split("=")[0]).append("\', NULL, \'").append(themesList.get(i).split("=")[1].replace("\'", "")).append("\', ");
            strb.append(String.valueOf(i + 1)).append(")");
            if (i < themesList.size() - 1) {
                strb.append(",\n");
            } else if (i == themesList.size() - 1) {
                strb.append(";\n\n");
            }
        }
        FileUtils.writeStringToFile(FileUtils.getFile(getSqlFile(destinationFolder)), strb.toString(), true);
    }

    public void createSqlCritere(String i18NPath, String destinationFolder) throws IOException {
        List<String> criteres = FileUtils.readLines(getI18nDefaultFile(i18NPath, "criterion"));
        List<String> themesList = FileUtils.readLines(getI18nDefaultFile(i18NPath, "theme"));
        StringBuilder strb = new StringBuilder();
        strb.append("INSERT IGNORE INTO `CRITERION` (`CD_CRITERION`, `DESCRIPTION`, `LABEL`, `URL`, `RANK`) VALUES\n");
        for (int i = 0; i < criteres.size(); i++) {
            strb.append("(\'").append(criteres.get(i).split("=")[0]);
            strb.append("\', \'").append(criteres.get(i).split("=")[1].replace("\'", ""));
            strb.append("\', \'").append(criteres.get(i).split("=")[0].substring(vpc.getReferentiel().length()).replace("-", "."));
            strb.append("\', \'\', ");
            strb.append(String.valueOf(i + 1)).append(")");
            if (i < criteres.size() - 1) {
                strb.append(",\n");
            } else if (i == criteres.size() - 1) {
                strb.append(";\n\n");
            }
        }
        strb.append("UPDATE `CRITERION` SET `reference_ID_REFERENCE` = (SELECT `ID_REFERENCE` FROM `REFERENCE` WHERE `CD_REFERENCE` LIKE \'");
        strb.append(vpc.getReferentiel().replace(".", "").toUpperCase());
        strb.append("\') WHERE `CD_CRITERION` LIKE \'");
        strb.append(vpc.getReferentiel().replace(".", "")).append("-%\';\n");
        for (int i = 0; i < themesList.size(); i++) {
            strb.append("UPDATE `CRITERION` SET `theme_ID_THEME` = (SELECT `ID_THEME` FROM `THEME` WHERE `CD_THEME` LIKE \'");
            strb.append(themesList.get(i).split("=")[0]);
            strb.append("\') WHERE `CD_CRITERION` LIKE \'");
            strb.append(themesList.get(i).split("=")[0]).append("-%\';\n");
            if (i == themesList.size() - 1) {
                strb.append("\n");
            }
        }
        FileUtils.writeStringToFile(FileUtils.getFile(getSqlFile(destinationFolder)), strb.toString(), true);
    }

    public void createSqlParameters(String i18NPath, String destinationFolder) throws IOException {
        StringBuilder strb = new StringBuilder();
        strb.append("INSERT IGNORE INTO `PARAMETER` (`Id_Parameter_Element`, `Parameter_Value`, `Is_Default`) VALUES\n");
        strb.append("(5, \'").append(vpc.getReferentiel().replace(".", "").toUpperCase()).append(";LEVEL_1\', b\'0\'),\n");
        strb.append("(5, \'").append(vpc.getReferentiel().replace(".", "").toUpperCase()).append(";LEVEL_2\', b\'0\'),\n");
        strb.append("(5, \'").append(vpc.getReferentiel().replace(".", "").toUpperCase()).append(";LEVEL_3\', b\'0\');\n\n");
        FileUtils.writeStringToFile(FileUtils.getFile(getSqlFile(destinationFolder)), strb.toString(), true);
    }

    public void createSqlTest(String i18NPath, String destinationFolder) throws IOException {
        List<String> tests = FileUtils.readLines(getI18nDefaultFile(i18NPath, "rule"));
        List<String> criteres = FileUtils.readLines(getI18nDefaultFile(i18NPath, "criterion"));
        StringBuilder strb = new StringBuilder();
        strb.append("INSERT IGNORE INTO `TEST` (`Cd_Test`, `Description`, `Label`, `Rank`, `Weight`, `Rule_Archive_Name`, `Rule_Class_Name`, `Id_Decision_Level`, `Id_Level`, `Id_Scope`, `Rule_Design_Url`, `No_Process`) VALUES\n");
        for (int i = 0; i < tests.size(); i += 2) {
            strb.append("(\'").append(tests.get(i).split("=")[0]).append("\', \'\', \'");
            strb.append(tests.get(i).substring(vpc.getReferentiel().replace(".", "").length() + 1).split("=")[0].replace("-", ".")).append("\', ");
            strb.append(String.valueOf(i + 1)).append(", ").append("\'1.0\', \'");
            strb.append(vpc.getReferentiel().replace(".", "").replace(" ", "").toLowerCase()).append("\', \'");
            strb.append(vpc.getPackageString()).append('.');
            strb.append(vpc.getReferentiel().replace(".", "").toLowerCase()).append(".");
            strb.append(String.valueOf(vpc.getClassRule().get(i - (i / 2)))).append("\', ");
            strb.append("NULL, 1, 1, \'\', b\'0\')");
            if (i < tests.size() - 1) {
                strb.append(",\n");
            } else if (i == tests.size() - 1) {
                strb.append(";\n\n");
            }
        }
        for (int i = 0; i < criteres.size(); i += 2) {
            strb.append("UPDATE `TEST` SET `Id_Criterion` = (SELECT `ID_CRITERION` FROM `CRITERION` WHERE `CD_CRITERION` LIKE \'");
            strb.append(criteres.get(i).split("=")[0]);
            strb.append("\') WHERE `Cd_Test` LIKE \'");
            strb.append(criteres.get(i).split("=")[0]).append("-%\';\n");
            if (i == criteres.size() - 1) {
                strb.append("\n");
            }
        }
        FileUtils.writeStringToFile(FileUtils.getFile(getSqlFile(destinationFolder)), strb.toString(), true);
    }
}

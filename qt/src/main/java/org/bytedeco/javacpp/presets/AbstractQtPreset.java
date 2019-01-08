package org.bytedeco.javacpp.presets;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.bytedeco.javacpp.ClassProperties;
import org.bytedeco.javacpp.LoadEnabled;
import org.bytedeco.javacpp.tools.Info;
import org.bytedeco.javacpp.tools.InfoMap;
import org.bytedeco.javacpp.tools.InfoMapper;

abstract class AbstractQtPreset implements InfoMapper, LoadEnabled {

  /**
   * Class property for includes
   */
  private static final String INCLUDE = "platform.include";

  /**
   * Class property for include path
   */
  private static final String INCLUDE_PATH = "platform.includepath";

  /**
   * Class property for library name
   */
  private static final String LIBRARY = "platform.library";

  /**
   * Class property for preload path
   */
  private static final String PRELOAD_PATH = "platform.preloadpath";

  /**
   * Environment variable for path to Qt include directory
   */
  private static final String QT_INCLUDE = "QT_INCLUDE";

  /**
   * Environment variable for path to Qt lib directory
   */
  private static final String QT_LIB = "QT_LIB";

  private final Logger log = Logger.getLogger(getClass().getName());

  @Override
  public void init(ClassProperties properties) {
    String libraryName = getClass().getSimpleName().replace("Preset", "");
    log.finest("Initial properties for " + libraryName + " preset: " + properties);

    // Set library name
    properties.setProperty(LIBRARY, "jni" + libraryName);

    // Set include path
    String qtInclude = System.getenv(QT_INCLUDE);
    if (qtInclude != null && !properties.get(INCLUDE_PATH).contains(qtInclude)) {
      log.fine("Adding " + QT_INCLUDE + " to include path: " + qtInclude);
      properties.get(INCLUDE_PATH).add(qtInclude);
    }

    // Set lib path
    String qtLib = System.getenv(QT_LIB);
    if (qtLib != null && !properties.get(PRELOAD_PATH).contains(qtLib)) {
      log.fine("Adding " + QT_LIB + " to preload path: " + qtLib);
      properties.get(PRELOAD_PATH).add(qtLib);
    }

    // Map includes to header files
    List<String> includes = properties.get(INCLUDE);
    for (int i = 0; i < includes.size(); ++i) {
      String include = includes.get(i);
      if (!include.contains("/")) {
        String filename = include.endsWith(".h") ? include : include.toLowerCase() + ".h";
        includes.set(i, libraryName + "/" + filename);
      }
    }

    log.finest("Update properties for " + libraryName + " preset: " + properties);
  }

  @Override
  public void map(InfoMap infoMap) {
    // Defines
    infoMap.put(new Info(defineFalse()).define(false))
        .put(new Info(defineTrue()).define(true));

    // Enums
    for (String enumName : enums()) {
      if (!enumName.startsWith("Qt::") && enumName.contains("::")) {
        String valueType = enumName.replace("::", ".");
        infoMap.put(new Info(enumName).enumerate().pointerTypes(valueType));
      } else {
        infoMap.put(new Info(enumName).enumerate());
      }
    }

    infoMap.put(new Info(intEnums()));

    // Flags
    for (Map.Entry<String, String> entry : flags().entrySet()) {
      String flagType = entry.getKey();
      String enumType = entry.getValue().replace("::", ".");
      // TODO
//      infoMap.put(new Info(flagType).valueTypes("@Cast(\"" + flagType + "\") " + enumType));
      infoMap.put(new Info(flagType).skip());
    }
    infoMap.put(new Info(intFlags()));

    // Macros
    for (Map.Entry<String, String> macro : macros().entrySet()) {
      int argsIndex = macro.getKey().indexOf('(');
      String name = (argsIndex > -1) ? macro.getKey().substring(0, argsIndex) : macro.getKey();

      String text = "#define " + macro.getKey() + " " + macro.getValue();
      infoMap.put(new Info(name).cppText(text));
    }

    // Skip
    infoMap.put(new Info(skip()).skip());
  }

  // ----
  // Info
  // ----

  /**
   * C++ names to define as false
   */
  protected String[] defineFalse() {
    return new String[0];
  }

  /**
   * C++ names to define as true
   */
  protected String[] defineTrue() {
    return new String[0];
  }

  /**
   * C++ names of enumerations
   */
  protected String[] enums() {
    return new String[0];
  }

  /**
   * Map of flag name to enum name
   */
  protected Map<String, String> flags() {
    return Collections.emptyMap();
  }

  /**
   * C++ names of int enumerations
   */
  protected String[] intEnums() {
    return new String[0];
  }

  /**
   * C++ names of int flags
   */
  protected String[] intFlags() {
    return new String[0];
  }

  /**
   * Map of macro names to values
   */
  protected Map<String, String> macros() {
    return Collections.emptyMap();
  }

  /**
   * C++ names to skip
   */
  protected String[] skip() {
    return new String[0];
  }

  // --------
  // Patterns
  // --------

  protected String matchClass(String name) {
    return " *class (Q_[A-Z]+_EXPORT )?" + name + "( :.*)?";
  }

  protected String matchEnd() {
    return "};";
  }

  protected String matchEnum(String name) {
    return " *enum " + name + "( \\{ .*)?";
  }
}

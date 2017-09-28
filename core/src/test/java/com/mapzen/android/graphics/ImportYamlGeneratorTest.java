package com.mapzen.android.graphics;

import com.mapzen.android.graphics.model.RefillStyle;

import org.junit.Test;

import static com.mapzen.android.graphics.model.ThemeColor.PINK;
import static com.mapzen.android.graphics.model.ThemedMapStyle.NONE;
import static org.assertj.core.api.Assertions.assertThat;

public class ImportYamlGeneratorTest {

  public ImportYamlGenerator yamlGenerator = new ImportYamlGenerator();
  public RefillStyle refillStyle = new RefillStyle();

  @Test public void getImportYaml_shouldReturnCorrectString() throws Exception {
    String yaml = yamlGenerator.getImportYaml(refillStyle, 3, 5, PINK);
    assertThat(yaml).isEqualTo("{ import: [ refill-style.yaml, themes/label-3.yaml, "
        + "themes/detail-5.yaml, themes/color-pink.yaml ] }");
  }

  @Test public void getImportYaml_shouldReturnCorrectStringForNoLod() throws Exception {
    String yaml = yamlGenerator.getImportYaml(refillStyle, 3, NONE, PINK);
    assertThat(yaml).isEqualTo("{ import: [ refill-style.yaml, themes/label-3.yaml, "
        + "themes/color-pink.yaml ] }");
  }

  @Test public void getImportYaml_shouldReturnCorrectStringForNoColor() throws Exception {
    String yaml = yamlGenerator.getImportYaml(refillStyle, 3, 8, null);
    assertThat(yaml).isEqualTo("{ import: [ refill-style.yaml, themes/label-3.yaml, "
        + "themes/detail-8.yaml ] }");
  }

  @Test public void getImportYaml_shouldReturnCorrectStringForNoLodNoColor() throws Exception {
    String yaml = yamlGenerator.getImportYaml(refillStyle, 3, NONE, null);
    assertThat(yaml).isEqualTo("{ import: [ refill-style.yaml, themes/label-3.yaml ] }");
  }
}

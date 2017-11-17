package io.servide.common.spigot.config;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;

@JsonSerialize(using = ConfigSerializer.class)
@JsonDeserialize(using = ConfigDeserializer.class)
public class ModuleConfig {

  private String moduleName;
  private Boolean enabled;
  private List<ModuleConfig> children;

  public String getModuleName() {
    return this.moduleName;
  }

  public void setModuleName(String moduleName) {
    this.moduleName = moduleName;
  }

  public List<ModuleConfig> getChildren() {
    return this.children;
  }

  public void setChildren(List<ModuleConfig> children) {
    this.children = children;
  }

  public Boolean isEnabled() {
    return this.enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

}

/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * Copyright 2018 Nextdoor.com, Inc
 *
 */

package com.nextdoor.bender.operation.substitution.regex;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaDefault;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaDescription;
import com.nextdoor.bender.operation.substitution.SubstitutionConfig;

@JsonTypeName("RegexSubstitution")
@JsonSchemaDescription("Substitutes event field value for another event field value. Note the source "
    + "field and destination field can be the same.")
public class RegexSubstitutionConfig extends SubstitutionConfig {
  public RegexSubstitutionConfig() {}

  public static class RegexSubField {
    @JsonSchemaDescription("Regex group name identifying the field.")
    @JsonProperty(required = true)
    private String regexGroupName;

    @JsonSchemaDescription("Data type of match group field. If type coercion does not succeed then "
        + "field is ignored.")
    @JsonProperty(required = true)
    private RegexSubFieldType type;

    @JsonSchemaDescription("Name or path of the new field.")
    @JsonProperty(required = true)
    private String key;

    public static enum RegexSubFieldType {
      STRING, NUMBER, BOOLEAN
    }

    public RegexSubField() {}

    public RegexSubField(String regexGroupName, RegexSubFieldType type, String key) {
      this.regexGroupName = regexGroupName;
      this.type = type;
      this.key = key;
    }

    public String getRegexGroupName() {
      return this.regexGroupName;
    }

    public void setRegexGroupName(String regexGroupName) {
      this.regexGroupName = regexGroupName;
    }

    public RegexSubFieldType getType() {
      return type;
    }

    public void setType(RegexSubFieldType type) {
      this.type = type;
    }

    public String getKey() {
      return this.key;
    }

    public void setKey(String key) {
      this.key = key;
    }
  }

  public RegexSubstitutionConfig(List<String> srcFields, String pattern, List<RegexSubField> fields,
      boolean removeSrcField, boolean failSrcNotFound, boolean failDstNotFound) {
    super(null, failDstNotFound);
    this.srcFields = srcFields;
    this.pattern = pattern;
    this.fields = fields;
    this.removeSrcField = removeSrcField;
    this.failSrcNotFound = failSrcNotFound;
  }

  @JsonIgnore
  private String key;

  @JsonSchemaDescription("Regex pattern with match groups. See https://docs.oracle.com/javase/8/docs/api/java/util/regex/Pattern.html")
  @JsonProperty(required = true)
  private String pattern;

  @JsonSchemaDescription("Source fields to pull value from and apply regex to. If multiple fields are provided the "
      + "first non-null valued one is used.")
  @JsonProperty(required = true)
  private List<String> srcFields;

  @JsonSchemaDescription("List of fields to create from matching regex groups.")
  @JsonSchemaDefault(value = "{}")
  @JsonProperty(required = true)
  private List<RegexSubField> fields = Collections.emptyList();

  @JsonSchemaDescription("Removes the source field after applying this substitution.")
  @JsonSchemaDefault(value = "false")
  @JsonProperty(required = false)
  private Boolean removeSrcField = false;

  @JsonSchemaDescription("Fail if source fields do not match regex or are not found.")
  @JsonProperty(required = false)
  @JsonSchemaDefault(value = "true")
  private Boolean failSrcNotFound = true;

  public String getPattern() {
    return this.pattern;
  }

  public void setPattern(String pattern) {
    this.pattern = pattern;
  }

  public void setSrcFields(List<String> srcFields) {
    this.srcFields = srcFields;
  }

  public List<String> getSrcFields() {
    return this.srcFields;
  }

  public List<RegexSubField> getFields() {
    return this.fields;
  }

  public void setFields(List<RegexSubField> fields) {
    this.fields = fields;
  }

  public Boolean getRemoveSrcField() {
    return this.removeSrcField;
  }

  public void setRemoveSrcField(Boolean removeSrcField) {
    this.removeSrcField = removeSrcField;
  }

  public Boolean getFailSrcNotFound() {
    return this.failSrcNotFound;
  }

  public void setFailSrcNotFound(Boolean failSrcNotFound) {
    this.failSrcNotFound = failSrcNotFound;
  }

  @Override
  public Class<RegexSubstitutionFactory> getFactoryClass() {
    return RegexSubstitutionFactory.class;
  }
}

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
 * Copyright 2017 Nextdoor.com, Inc
 *
 */

package com.nextdoor.bender.operations.geo;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaDefault;
import com.kjetland.jackson.jsonSchema.annotations.JsonSchemaDescription;
import com.nextdoor.bender.operation.OperationConfig;

@JsonTypeName("GeoIpOperation")
@JsonSchemaDescription("Looks up geo location provided an IP address and adds a map field to the "
    + "payload that optionally contains country_name, country_iso_code, subdivision_name, "
    + "subdivision_iso_code, city_name, postal_code, and location. For example "
    + "\n\n input = {\"ip\": \"")
public class GeoIpOperationConfig extends OperationConfig {

  public static enum GeoProperty {
    COUNTRY_NAME, COUNTRY_ISO_CODE, SUBDIVISION_NAME, SUBDIVISION_ISO_CODE, CITY_NAME, POSTAL_CODE, LOCATION
  }

  @JsonSchemaDescription("Field containing an IP address string in the payload")
  @JsonProperty(required = true)
  private String ipAddrField;

  @JsonSchemaDescription("Field name to save the geo data to")
  @JsonProperty(required = true)
  @JsonSchemaDefault(value = "geo_ip")
  private String destinationFieldName = "geo_ip";

  @JsonSchemaDescription("S3 path to the GeoLite2 database file. Prefix with s3://")
  @JsonProperty(required = true)
  private String geoLiteDb;

  @JsonSchemaDescription("List of geo properties to include")
  @JsonProperty(required = false)
  @JsonSchemaDefault(value = "[]")
  private List<GeoProperty> geoProperties = Collections.emptyList();

  @JsonSchemaDescription("If ip lookup fails also fail the operation. Doing so will filter the event.")
  @JsonProperty(required = false)
  @JsonSchemaDefault(value = "false")
  private Boolean failOnNotFound = false;

  @Override
  public Class<GeoIpOperationFactory> getFactoryClass() {
    return GeoIpOperationFactory.class;
  }

  public String getIpAddrField() {
    return this.ipAddrField;
  }

  public void setIpAddrField(String ipAddrField) {
    this.ipAddrField = ipAddrField;
  }

  public String getDestinationFieldName() {
    return this.destinationFieldName;
  }

  public void setDestinationFieldName(String destinationFieldName) {
    this.destinationFieldName = destinationFieldName;
  }

  public String getGeoLiteDb() {
    return this.geoLiteDb;
  }

  public void setGeoLiteDb(String geoLiteDb) {
    this.geoLiteDb = geoLiteDb;
  }

  public List<GeoProperty> getGeoProperties() {
    return this.geoProperties;
  }

  public void setGeoProperties(List<GeoProperty> geoProperties) {
    this.geoProperties = geoProperties;
  }

  public Boolean getFailOnNotFound() {
    return this.failOnNotFound;
  }

  public void setFailOnNotFound(boolean failOnNotFound) {
    this.failOnNotFound = failOnNotFound;
  }
}

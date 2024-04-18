package com.redhat.quarkus.models;

import java.util.List;

public class Release {
    String version;
    String quarkusCoreVersion;
    String upstreamQuarkusCoreVersion;
    List<MemberBOM> memberBOMs;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getQuarkusCoreVersion() {
        return quarkusCoreVersion;
    }

    public void setQuarkusCoreVersion(String quarkusCoreVersion) {
        this.quarkusCoreVersion = quarkusCoreVersion;
    }

    public String getUpstreamQuarkusCoreVersion() {
        return upstreamQuarkusCoreVersion;
    }

    public void setUpstreamQuarkusCoreVersion(String upstreamQuarkusCoreVersion) {
        this.upstreamQuarkusCoreVersion = upstreamQuarkusCoreVersion;
    }

    public List<MemberBOM> getMemberBOMs() {
        return memberBOMs;
    }

    public void setMemberBOMs(List<MemberBOM> memberBOMs) {
        this.memberBOMs = memberBOMs;
    }

    @Override
    public String toString() {
        return "Release{" +
                "version='" + version + '\'' +
                ", quarkusCoreVersion='" + quarkusCoreVersion + '\'' +
                ", upstreamQuarkusCoreVersion='" + upstreamQuarkusCoreVersion + '\'' +
                ", memberBOMs=" + memberBOMs +
                '}';
    }
}

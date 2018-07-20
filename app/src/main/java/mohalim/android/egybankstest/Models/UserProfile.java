package mohalim.android.egybankstest.Models;

public class UserProfile {
    String aboutMe, experience, education, skills,
           certifications, courses, langauge, birthdate,
           nationality, phone, mobile, email, location,
           extraInfo;

    public UserProfile(String aboutMe, String experience, String education, String skills, String certifications, String courses, String langauge, String birthdate, String nationality, String phone, String mobile, String email, String location, String extraInfo) {
        this.aboutMe = aboutMe;
        this.experience = experience;
        this.education = education;
        this.skills = skills;
        this.certifications = certifications;
        this.courses = courses;
        this.langauge = langauge;
        this.birthdate = birthdate;
        this.nationality = nationality;
        this.phone = phone;
        this.mobile = mobile;
        this.email = email;
        this.location = location;
        this.extraInfo = extraInfo;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public String getCourses() {
        return courses;
    }

    public void setCourses(String courses) {
        this.courses = courses;
    }

    public String getLangauge() {
        return langauge;
    }

    public void setLangauge(String langauge) {
        this.langauge = langauge;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}

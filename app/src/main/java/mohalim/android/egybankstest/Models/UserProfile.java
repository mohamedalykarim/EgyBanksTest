package mohalim.android.egybankstest.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable {
    String name,profileImage, email, aboutMe, experience, education, skills,
           certifications, courses, langauge, birthdate,
           nationality, phone, mobile, location,
           extraInfo;
    int points;

    public UserProfile() {
    }

    public UserProfile(String name, String profileImage, String email, String aboutMe, String experience, String education, String skills, String certifications, String courses, String langauge, String birthdate, String nationality, String phone, String mobile, String location, String extraInfo, int points) {
        this.name = name;
        this.profileImage = profileImage;
        this.email = email;
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
        this.location = location;
        this.extraInfo = extraInfo;
        this.points = points;
    }

    protected UserProfile(Parcel in) {
        name = in.readString();
        profileImage = in.readString();
        email = in.readString();
        aboutMe = in.readString();
        experience = in.readString();
        education = in.readString();
        skills = in.readString();
        certifications = in.readString();
        courses = in.readString();
        langauge = in.readString();
        birthdate = in.readString();
        nationality = in.readString();
        phone = in.readString();
        mobile = in.readString();
        location = in.readString();
        extraInfo = in.readString();
        points = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(profileImage);
        dest.writeString(email);
        dest.writeString(aboutMe);
        dest.writeString(experience);
        dest.writeString(education);
        dest.writeString(skills);
        dest.writeString(certifications);
        dest.writeString(courses);
        dest.writeString(langauge);
        dest.writeString(birthdate);
        dest.writeString(nationality);
        dest.writeString(phone);
        dest.writeString(mobile);
        dest.writeString(location);
        dest.writeString(extraInfo);
        dest.writeInt(points);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

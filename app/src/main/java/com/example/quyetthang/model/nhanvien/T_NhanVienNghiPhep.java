package com.example.quyetthang.model.nhanvien;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_NhanVienNghiPhep {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("maNV")
    @Expose
    private String maNV;

    @SerializedName("hoTen")
    @Expose
    private String hoTen;

    @SerializedName("loaiNghiPhep")
    @Expose
    private String loaiNghiPhep;

    @SerializedName("soNgayNghi")
    @Expose
    private double soNgayNghi;

    @SerializedName("ngayNghiText")
    @Expose
    private String ngayNghiText;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("tinhTrang")
    @Expose
    private String tinhTrang;

    @SerializedName("ghiChu")
    @Expose
    private String ghiChu;

    @SerializedName("phongBan")
    @Expose
    private String phongBan;

    @SerializedName("result")
    @Expose
    private Integer result;

    @SerializedName("nguoiDuyet")
    @Expose
    private String nguoiDuyet;

    @SerializedName("nguoiXacNhan")
    @Expose
    private String nguoiXacNhan;

    @SerializedName("tuNgayText")
    @Expose
    private String tuNgayText;

    @SerializedName("denNgayText")
    @Expose
    private String denNgayText;

    @SerializedName("loaiCongTac")
    @Expose
    private String loaiCongTac;

    @SerializedName("chiPhiCongTac")
    @Expose
    private double chiPhiCongTac;

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getLoaiNghiPhep() {
        return loaiNghiPhep;
    }

    public void setLoaiNghiPhep(String loaiNghiPhep) {
        this.loaiNghiPhep = loaiNghiPhep;
    }

    public double getSoNgayNghi() {
        return soNgayNghi;
    }

    public void setSoNgayNghi(double soNgayNghi) {
        this.soNgayNghi = soNgayNghi;
    }

    public String getNgayNghiText() {
        return ngayNghiText;
    }

    public void setNgayNghiText(String ngayNghiText) {
        this.ngayNghiText = ngayNghiText;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        id = id;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getNguoiXacNhan() {
        return nguoiXacNhan;
    }

    public void setNguoiXacNhan(String nguoiXacNhan) {
        this.nguoiXacNhan = nguoiXacNhan;
    }

    public String getNguoiDuyet() {
        return nguoiDuyet;
    }

    public void setNguoiDuyet(String nguoiDuyet) {
        this.nguoiDuyet = nguoiDuyet;
    }

    public String getDenNgayText() {
        return denNgayText;
    }

    public void setDenNgayText(String denNgayText) {
        this.denNgayText = denNgayText;
    }

    public String getTuNgayText() {
        return tuNgayText;
    }

    public void setTuNgayText(String tuNgayText) {
        this.tuNgayText = tuNgayText;
    }

    public double getChiPhiCongTac() {
        return chiPhiCongTac;
    }

    public void setChiPhiCongTac(double chiPhiCongTac) {
        this.chiPhiCongTac = chiPhiCongTac;
    }

    public String getLoaiCongTac() {
        return loaiCongTac;
    }

    public void setLoaiCongTac(String loaiCongTac) {
        this.loaiCongTac = loaiCongTac;
    }
}

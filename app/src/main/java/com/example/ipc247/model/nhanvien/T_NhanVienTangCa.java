package com.example.ipc247.model.nhanvien;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_NhanVienTangCa {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("maNV")
    @Expose
    private String maNV;

    @SerializedName("hoTen")
    @Expose
    private String hoTen;

    @SerializedName("phongBan")
    @Expose
    private String phongBan;

    @SerializedName("ghiChu")
    @Expose
    private String ghiChu;

    @SerializedName("loaiTangCa")
    @Expose
    private String loaiTangCa;

    @SerializedName("ngayTangCaText")
    @Expose
    private String ngayTangCaText;

    @SerializedName("nguoiDuyet")
    @Expose
    private String nguoiDuyet;

    @SerializedName("nguoiXacNhan")
    @Expose
    private String nguoiXacNhan;

    @SerializedName("tinhTrang")
    @Expose
    private String tinhTrang;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("result")
    @Expose
    private Integer result;

    @SerializedName("tuGioText")
    @Expose
    private String tuGioText;

    @SerializedName("denGioText")
    @Expose
    private String denGioText;

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

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String getLoaiTangCa() {
        return loaiTangCa;
    }

    public void setLoaiTangCa(String loaiTangCa) {
        this.loaiTangCa = loaiTangCa;
    }

    public String getNgayTangCaText() {
        return ngayTangCaText;
    }

    public void setNgayTangCaText(String ngayTangCaText) {
        this.ngayTangCaText = ngayTangCaText;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getNguoiDuyet() {
        return nguoiDuyet;
    }

    public void setNguoiDuyet(String nguoiDuyet) {
        this.nguoiDuyet = nguoiDuyet;
    }

    public String getNguoiXacNhan() {
        return nguoiXacNhan;
    }

    public void setNguoiXacNhan(String nguoiXacNhan) {
        this.nguoiXacNhan = nguoiXacNhan;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getDenGioText() {
        return denGioText;
    }

    public void setTuGioText(String tuGioText) {
        this.tuGioText = tuGioText;
    }

    public String getTuGioText() {
        return tuGioText;
    }

    public void setDenGioText(String denGioText) {
        this.denGioText = denGioText;
    }
}

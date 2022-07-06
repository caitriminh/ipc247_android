package com.example.quyetthang.model.giaoviec;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class T_GiaoViec {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("maCongViec")
    @Expose
    private String maCongViec;

    @SerializedName("nhomCongViec")
    @Expose
    private String nhomCongViec;

    @SerializedName("ngayBatDauText")
    @Expose
    private String ngayBatDauText;

    @SerializedName("ngayKetThucDuKienText")
    @Expose
    private String ngayKetThucDuKienText;

    @SerializedName("ngayHoanThanhText")
    @Expose
    private String ngayHoanThanhText;

    @SerializedName("cauHinh")
    @Expose
    private String cauHinh;

    @SerializedName("ghiChu")
    @Expose
    private String ghiChu;

    @SerializedName("idDanhGia")
    @Expose
    private String idDanhGia;

    @SerializedName("phongBan")
    @Expose
    private String phongBan;

    @SerializedName("company")
    @Expose
    private String company;

    @SerializedName("hoTenNhanViec")
    @Expose
    private String hoTenNhanViec;

    @SerializedName("tinhTrang")
    @Expose
    private String tinhTrang;

    @SerializedName("heSo")
    @Expose
    private Double heSo;

    @SerializedName("thoiGian")
    @Expose
    private Double thoiGian;

    @SerializedName("message")
    @Expose
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhongBan() {
        return phongBan;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String getNgayBatDauText() {
        return ngayBatDauText;
    }

    public void setNgayBatDauText(String ngayBatDauText) {
        this.ngayBatDauText = ngayBatDauText;
    }

    public String getNgayHoanThanhText() {
        return ngayHoanThanhText;
    }

    public void setNgayHoanThanhText(String ngayHoanThanhText) {
        this.ngayHoanThanhText = ngayHoanThanhText;
    }

    public String getNgayKetThucDuKienText() {
        return ngayKetThucDuKienText;
    }

    public void setNgayKetThucDuKienText(String ngayKetThucDuKienText) {
        this.ngayKetThucDuKienText = ngayKetThucDuKienText;
    }

    public Double getHeSo() {
        return heSo;
    }

    public void setHeSo(Double heSo) {
        this.heSo = heSo;
    }

    public Double getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Double thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getIdDanhGia() {
        return idDanhGia;
    }

    public void setIdDanhGia(String idDanhGia) {
        this.idDanhGia = idDanhGia;
    }

    public String getCauHinh() {
        return cauHinh;
    }

    public void setCauHinh(String cauHinh) {
        this.cauHinh = cauHinh;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getHoTenNhanViec() {
        return hoTenNhanViec;
    }

    public void setHoTenNhanViec(String hoTenNhanViec) {
        this.hoTenNhanViec = hoTenNhanViec;
    }

    public String getMaCongViec() {
        return maCongViec;
    }

    public void setMaCongViec(String maCongViec) {
        this.maCongViec = maCongViec;
    }

    public String getNhomCongViec() {
        return nhomCongViec;
    }

    public void setNhomCongViec(String nhomCongViec) {
        this.nhomCongViec = nhomCongViec;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

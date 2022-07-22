CREATE DATABASE EduSys
GO
USE EduSys
GO 

CREATE TABLE NhanVien(
	MaNV NVARCHAR(50) NOT NULL,
	MatKhau NVARCHAR(50) NOT NULL,
	HoTen NVARCHAR(50) NOT NULL,
	VaiTro BIT NOT NULL,
	PRIMARY KEY(MaNV)
)
GO

CREATE TABLE ChuyenDe(
	MaCD NCHAR(5) NOT NULL,
	TenCD NVARCHAR(50) NOT NULL,
	HocPhi FLOAT NOT NULL,
	ThoiLuong INT NOT NULL,
	Hinh NVARCHAR(50) NOT NULL,
	MoTa NVARCHAR(255) NOT NULL,
	PRIMARY KEY(MaCD)
)
GO

CREATE TABLE KhoaHoc(
	MaKH INT IDENTITY(1,1) NOT NULL,
	MaCD NCHAR(5) NOT NULL,
	HocPhi FLOAT NOT NULL,
	ThoiLuong INT NOT NULL,
	NgayKG DATE NOT NULL,
	GhiChu NVARCHAR(50) NULL,
	MaNV NVARCHAR(50) NOT NULL,
	NgayTao DATE NOT NULL,
	PRIMARY KEY(MaKH),
	FOREIGN KEY(MaCD) REFERENCES dbo.ChuyenDe(MaCD) ON DELETE NO ACTION ON UPDATE CASCADE,
	FOREIGN KEY(MaNV) REFERENCES dbo.NhanVien(MaNV) ON DELETE NO ACTION ON UPDATE CASCADE
)
GO

CREATE TABLE NguoiHoc(
	MaNH NVARCHAR(7) NOT NULL,
	HoTen NVARCHAR(50) NOT NULL,
	NgaySinh DATE NOT NULL,
	GioiTinh BIT NOT NULL,
	DienThoai NVARCHAR(50) NOT NULL,
	Email NVARCHAR(50) NOT NULL,
	GhiChu NVARCHAR(MAX) NULL,
	MaNV NVARCHAR(50) NOT NULL,
	NgayTao DATE DEFAULT GETDATE(),
	PRIMARY KEY(MaNH),
	FOREIGN KEY(MaNV) REFERENCES dbo.NhanVien(MaNV) ON DELETE NO ACTION ON UPDATE CASCADE
)
GO

CREATE TABLE HocVien(
	MaHV INT IDENTITY(1,1) NOT NULL,
	MaKH INT NOT NULL,
	MaNH NVARCHAR(7) NOT NULL,
	Diem FLOAT NOT NULL,
	PRIMARY KEY(MaHV),
	FOREIGN KEY(MaNH) REFERENCES dbo.NguoiHoc(MaNH) ON DELETE NO ACTION ON UPDATE CASCADE,
	FOREIGN KEY(MaKH) REFERENCES dbo.KhoaHoc(MaKH)
)

SELECT*FROM dbo.NhanVien
SELECT*FROM dbo.HocVien
SELECT*FROM dbo.KhoaHoc
SELECT*FROM dbo.ChuyenDe
SELECT*FROM dbo.NguoiHoc



	INSERT INTO dbo.NhanVien(MaNV,MatKhau,HoTen,VaiTro)
	VALUES('chienhq','hai3456',N'Hạ Quyết Chiến',1)
	INSERT INTO dbo.NhanVien(MaNV,MatKhau,HoTen,VaiTro)
	VALUES('anhhv','123456',N'Hạ Việt Anh',0)
	INSERT INTO dbo.NhanVien(MaNV,MatKhau,HoTen,VaiTro)
	VALUES('phuongtt','30082002',N'Trần Thanh Phương',1)
	INSERT INTO dbo.NhanVien(MaNV,MatKhau,HoTen,VaiTro)
	VALUES('phuongtd','phuongyl',N'Trần Đức Phương',0)
	INSERT INTO dbo.NhanVien(MaNV,MatKhau,HoTen,VaiTro)
	VALUES('thuylm','thuy01',N'Lê Minh Thụy',1)

	INSERT INTO dbo.ChuyenDe(MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa)
	VALUES('CD01',N'Java',900000,90,'JAAV.png',N'Khó')
	INSERT INTO dbo.ChuyenDe(MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa)
	VALUES('CD02',N'C#',800000,90,'ASNE.png',N'Vào Học Sẽ Biết')
	INSERT INTO dbo.ChuyenDe(MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa)
	VALUES('CD03',N'Web',750000,90,'WEBU.jpg',N'Khó')
	INSERT INTO dbo.ChuyenDe(MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa)
	VALUES('CD04',N'Thiết Kế Đồ Họa',950000,90,'noImage.png',N'Khó')
	INSERT INTO dbo.ChuyenDe(MaCD,TenCD,HocPhi,ThoiLuong,Hinh,MoTa)
	VALUES('CD05',N'Mobie',900000,90,'MOWE.png',N'Khó')

	INSERT INTO dbo.KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao)
	VALUES('CD01',900000,90,'2020-02-02',N'Số Lượng Lớn','chienhq','2019-02-02')
	INSERT INTO dbo.KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao)
	VALUES('CD02',700000,90,'2020-03-03',N'Ít','anhhv','2019-03-03')
	INSERT INTO dbo.KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao)
	VALUES('CD03',600000,90,'2020-04-04',N'','phuongtt','2019-04-04')
	INSERT INTO dbo.KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao)
	VALUES('CD04',800000,90,'2020-06-06',N'Số Lượng Lớn','phuongtd','2019-06-06')
	INSERT INTO dbo.KhoaHoc(MaCD,HocPhi,ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao)
	VALUES('CD05',750000,90,'2020-09-09',N'','thuylm','2019-09-09')

	INSERT INTO dbo.NguoiHoc(MaNH,HoTen,NgaySinh,GioiTinh,DienThoai,Email,GhiChu,MaNV)
	VALUES('NH01',N'Nguyễn Văn Linh','2002-01-01',1,'0987654321','linhnv@gmail.com',NULL,'chienhq')
	INSERT INTO dbo.NguoiHoc(MaNH,HoTen,NgaySinh,GioiTinh,DienThoai,Email,GhiChu,MaNV)
	VALUES('NH02',N'Nguyễn Văn Tuấn','2002-02-02',1,'0987655555','tuannv@gmail.com',NULL,'anhhv')
	INSERT INTO dbo.NguoiHoc(MaNH,HoTen,NgaySinh,GioiTinh,DienThoai,Email,GhiChu,MaNV)
	VALUES('NH03',N'Vũ Thị Hiền','2002-03-03',0,'0987654444','hienvt@gmail.com',NULL,'phuongtt')
	INSERT INTO dbo.NguoiHoc(MaNH,HoTen,NgaySinh,GioiTinh,DienThoai,Email,GhiChu,MaNV)
	VALUES('NH04',N'Nguyễn Thị Quỳnh','2002-04-04',0,'0987654333','quynhnt@gmail.com',NULL,'phuongtd')
	INSERT INTO dbo.NguoiHoc(MaNH,HoTen,NgaySinh,GioiTinh,DienThoai,Email,GhiChu,MaNV)
	VALUES('NH05',N'Cao Văn Hùng','2002-05-05',1,'0987654322','hungcv@gmail.com',NULL,'thuylm')

	INSERT INTO dbo.HocVien(MaKH,MaNH,Diem)
	VALUES(1,'NH01',9.0)
	INSERT INTO dbo.HocVien(MaKH,MaNH,Diem)
	VALUES(2,'NH02',9.0)
	INSERT INTO dbo.HocVien(MaKH,MaNH,Diem)
	VALUES(3,'NH03',10.0)
	INSERT INTO dbo.HocVien(MaKH,MaNH,Diem)
	VALUES(4,'NH04',5.6)
	INSERT INTO dbo.HocVien(MaKH,MaNH,Diem)
	VALUES(5,'NH05',6.0)

	DELETE FROM dbo.NhanVien  WHERE MaNV = 'chienhq'
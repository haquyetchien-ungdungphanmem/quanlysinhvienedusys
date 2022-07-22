	CREATE PROC SP_BangDiem(@MaKH INT)
	AS BEGIN
		SELECT NguoiHoc.MaNH,HoTen,Diem
		FROM dbo.HocVien JOIN dbo.NguoiHoc 
		ON NguoiHoc.MaNH = HocVien.MaNH
		WHERE MaKH = @MaKH
		ORDER BY Diem DESC
	END

	CREATE PROC SP_DiemChuyenDe
	AS BEGIN
	       SELECT TenCD AS ChuyenDe,
					COUNT(MaHV) AS SoHV,
					MIN(Diem) AS DiemThapNhat,
					MAX(Diem) AS DiemCaoNhat,
					AVG(Diem) AS DiemTrungBinh
		   FROM dbo.KhoaHoc 
				   JOIN dbo.HocVien ON HocVien.MaKH = KhoaHoc.MaKH
					JOIN dbo.ChuyenDe ON ChuyenDe.MaCD = KhoaHoc.MaCD
			GROUP BY TenCD
	END

	CREATE PROC SP_DoanhThu(@Year INT)
	AS BEGIN
	       SELECT TenCD AS ChuyenDe,
					COUNT(DISTINCT KhoaHoc.MaKH) AS SOKH,
					COUNT(MaHV) AS SoHV,
					SUM(KhoaHoc.HocPhi) AS DoanhThu,
					MIN(KhoaHoc.HocPhi) AS ThapNhat,
					MAX(KhoaHoc.HocPhi) AS CaoNhat,
					AVG(KhoaHoc.HocPhi) AS TrungBinh
		   FROM dbo.KhoaHoc 
			   JOIN dbo.HocVien ON HocVien.MaKH = KhoaHoc.MaKH
			   JOIN dbo.ChuyenDe ON ChuyenDe.MaCD = KhoaHoc.MaCD
		   WHERE YEAR(NgayKG) = @Year
		   GROUP BY TenCD
	   END

	   CREATE PROC SP_LuongNguoiHoc
	   AS BEGIN
	          SELECT YEAR(NgayTao) AS Nam,
			  COUNT(*) AS SoLuong,
			  MIN(NgayTao) AS DauTien,
			  MAX(NgayTao) AS CuoiCung
			  FROM dbo.NguoiHoc
			  GROUP BY YEAR(NgayTao)
	      END

# takin-utils-qrcode

	// default file
	File f = TakinQRCode.form(“hello world”).toFile()
	
	// full call.
	TakinQRcode.from(String).[withXXX()]*.toXXX()
	
	withXXX():
		withSize(int, int)
		withColor(onColor, offColor)
		withType(PNG/JPG/GIF/BMP)
		...
		
	toXXX():
		toFile([String])
		toStream([stream])
import {PDFDocument} from "pdf-lib";

const BASE_PATH = "/app/pdfs"

export const generateReceipt = async (booking) => {
  const pdfDoc = await PDFDocument.create();
  const page = pdfDoc.addPage([600, 400]);

  page.drawText(
    `
    Booking Receipt
    ----------------

    Booking ID : ${booking.id}
    Total Price : ${booking.totalPrice}
    Total Rooms : ${booking.totalRooms}
    Hotel Name : ${booking.hotelName}
    Username : ${booking.username}
    `,
    {
      x: 50,
      y: 350,
      size: 20,
    },
  );

  const pdfBytes = await pdfDoc.save();

  await Deno.mkdir(BASE_PATH, { recursive: true });

  const filePath = `${BASE_PATH}/receipt_${booking.id}.pdf`;

  await Deno.writeFile(filePath, pdfBytes);
  console.log("Receipt generated!");
};

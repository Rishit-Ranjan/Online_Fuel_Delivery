# Online Fuel Delivery App

A comprehensive Android application simulating a multirole fuel delivery ecosystem. This project demonstrates modern Android development practices, including MVVM architecture, Room database integration, and a sleek Material 3 user interface.

## 🚀 Key Features

### 👤 User Workspace
*   **Station Discovery**: Browse and compare nearby fuel stations with real-time distance and rating info.
*   **Interactive Ordering**: A dark-themed, intuitive "Order Gas" dialog.
    *   Select between **Petrol** and **Diesel**.
    *   Real-time price calculation based on volume.
    *   Quick-select volume presets (10L, 20L, 50L, 100L).
    *   Support for multiple payment methods (Cash on Delivery, Digital Wallet).
*   **Order Tracking**: View order history and current status.

### 🛠️ Admin Workspace
*   **Inventory Management**: Toggle "In Stock" or "Out of Stock" status for Petrol and Diesel at each station.
*   **Rate Control**: Update fuel prices per liter in real-time, instantly affecting the User's view.
*   **Station Management**: View and manage multiple station locations.

### 🚚 Driver Workspace (Simulated)
*   **Delivery Flow**: Simulated workspace for managing active deliveries and order confirmation.

## 🛠️ Tech Stack
*   **Language**: Java
*   **Architecture**: MVVM (Model-View-ViewModel) with Repository pattern.
*   **Database**: [Room Persistence Library](https://developer.android.com/training/data-storage/room) for local data management.
*   **UI Components**: Material 3, ViewBinding, BottomSheetDialogFragment, TabLayout.
*   **Lifecycle**: LiveData & ViewModel for reactive UI updates.

## 📸 Screenshots

<img width="270" alt="Station Discovery" src="https://github.com/user-attachments/assets/84d2e991-4e38-4d9a-a16c-24830e4663ee" /><br/><br/>
<img width="270" alt="Order Dialog" src="https://github.com/user-attachments/assets/b5cc31b3-c6b1-46ea-97cd-eecb0f037bbf" /><br/><br/>
<img width="270" alt="Admin Inventory" src="https://github.com/user-attachments/assets/864dff7f-0af3-48ac-872a-47647bb296ab" />

## 🚦 Getting Started

### Prerequisites
*   [Android Studio Ladybug](https://developer.android.com/studio) or newer.
*   Android SDK 34+.

### Setup Instructions
1.  **Clone the Repository**:
    ```bash
    git clone https://github.com/your-repo/online-fuel-delivery.git
    ```
2.  **Open in Android Studio**:
    *   Select **File > Open** and choose the project directory.
    *   Wait for Gradle sync to complete.
3.  **Configuration**:
    *   Create a `.env` file in the root directory (refer to `.env.example`).
    *   Add your `GEMINI_API_KEY` if utilizing the AI-assisted features.
4.  **Run the App**:
    *   Select an emulator or physical device.
    *   Click the **Run** button (Shift + F10).

## 🧪 Simulation Logic
This app uses a built-in **Role Switcher** at the bottom of the screen. You can instantly toggle between **User**, **Admin**, and **Driver** modes to test the end-to-end delivery lifecycle without logging out.

---
*Developed as a demonstration of a scalable, interactive Android architecture.*

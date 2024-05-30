package com.ucsal.notification.interfaces;

import com.ucsal.notification.models.Notification;

public interface NotificationChannel {
	void send(Notification notification);
}

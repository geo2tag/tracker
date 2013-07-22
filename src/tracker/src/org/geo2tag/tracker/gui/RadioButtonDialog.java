/*
 * Copyright 2010-2011  Vasily Romanikhin  bac1ca89@gmail.com
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * 3. The name of the author may not be used to endorse or promote
 *    products derived from this software without specific prior written
 *    permission.
 *
 * The advertising clause requiring mention in adverts must never be included.
 */

/*! ---------------------------------------------------------------
 * PROJ: OSLL/geo2tag
 * ---------------------------------------------------------------- */

package org.geo2tag.tracker.gui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Button;
 
public class RadioButtonDialog{
	protected Context m_context;
	private String m_title;
	private String[] m_items;
	private Button m_button; // THis button will be updated after dialog dissmiss
	private int m_selectedItem;
	
	public String[] getItems() {
		return m_items;
	}


	public void setItems(String[] myItems) {
		this.m_items = myItems;
	}


	
	public RadioButtonDialog(Context context, String title, String[] items, int selectedItem,
			Button button){
		m_context = context;
		m_title = title;
		m_items = items;
		m_selectedItem = selectedItem;
		m_button = button;
	}

	protected void itemSelected(DialogInterface dialog, int item){
		String str = getItems()[item];
		
		m_button.setText(str);
		dialog.dismiss();
	}
	
	public void show(){
		AlertDialog.Builder builder = new AlertDialog.Builder(m_context);
		builder.setTitle(m_title);
		builder.setSingleChoiceItems(m_items, m_selectedItem, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	itemSelected(dialog, item);
		    }
		});
		builder.create().show();
	}
	

}